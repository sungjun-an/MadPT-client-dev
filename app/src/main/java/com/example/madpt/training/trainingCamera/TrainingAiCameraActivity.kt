/* Copyright 2021 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================
*/

package com.example.madpt.training.trainingCamera

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.speech.tts.TextToSpeech
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.madpt.API.trainresult.PostTrainResultCall
import com.example.madpt.API.trainresult.Records
import com.example.madpt.API.trainresult.Train_result
import com.example.madpt.R
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.camera.CameraSource
import com.example.madpt.training.trainingCamera.data.Device
import com.example.madpt.training.trainingCamera.data.TrainingData
import com.example.madpt.training.trainingCamera.ml.*
import com.kakao.sdk.newtoneapi.TextToSpeechManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round

class TrainingAiCameraActivity : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_DIALOG = "dialog"
    }

    /** A [SurfaceView] for camera preview.   */
    private lateinit var surfaceView: SurfaceView

    /** Default pose estimation model is 1 (MoveNet Thunder)
     * 0 == MoveNet Lightning model
     * 1 == MoveNet Thunder model
     * 2 == MoveNet MultiPose model
     * 3 == PoseNet model
     **/
    private var modelPos = 1

    /** Default device is CPU */
    private var device = Device.CPU

    private var trainingList = ArrayList<testmodel>()
    private var trainingDataList: ArrayList<TrainingData> = ArrayList()
    private val staticTrainingList = arrayListOf<testmodel>()
    private var excrciseTimeList = ArrayList<Long>()
    private lateinit var Timer: TextView // test for timer
    private lateinit var Sets: TextView // test for sets
    private lateinit var Reps: TextView // test for laps
    private lateinit var Feedback: TextView // test for laps
    private lateinit var currentExcrcise_view: TextView
    private lateinit var nextExcrcise_view: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvFPS: TextView
    private lateinit var breakTime: TextView
    private lateinit var spnDevice: Spinner
    private lateinit var spnModel: Spinner
    private lateinit var spnTracker: Spinner
    private lateinit var vTrackerOption: View
    private lateinit var framechecker: TextView
    private lateinit var tvClassificationValue1: TextView
    private lateinit var tvClassificationValue2: TextView
    private lateinit var tvClassificationValue3: TextView
    private lateinit var swClassification: SwitchCompat
    private lateinit var vClassificationOption: View
    private var tts: TextToSpeech? = null
    private var cameraSource: CameraSource? = null
    private var isClassifyPose = false
    private var breakTimeInt = 0
    private val exerciseId = mapOf<String, Long>("PUSH UP" to 1, "SQUAT" to 2, "LUNGE" to 3, "DUMBBELL" to 4)
    private lateinit var dialog: LoadingDialog
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                openCamera()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                ErrorDialog.newInstance(getString(R.string.tfe_pe_request_permission))
                    .show(supportFragmentManager, FRAGMENT_DIALOG)
            }
        }
    private var changeModelListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // do nothing
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            changeModel(position)
        }
    }

    private var changeDeviceListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            changeDevice(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // do nothing
        }
    }

    private var changeTrackerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            changeTracker(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // do nothing
        }
    }

    private var setClassificationListener =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            showClassificationResult(isChecked)
            isClassifyPose = isChecked
            isPoseClassifier()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_ai_camera)
        // keep screen on while app is running
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        trainingList = intent.getParcelableArrayListExtra<testmodel>("trainList") as ArrayList<testmodel>
        staticTrainingList.addAll(trainingList)
        Timer = findViewById(R.id.timer)  // test for timer
        Sets = findViewById(R.id.sets) // test for sets
        Reps = findViewById(R.id.reps) // test for reps
        Feedback = findViewById(R.id.feedback) // test for reps
        currentExcrcise_view = findViewById(R.id.currentExcrcise)
        nextExcrcise_view = findViewById(R.id.nextExcrcise)
        tvScore = findViewById(R.id.tvScore)
        breakTime = findViewById(R.id.showBreakTime)
        tvFPS = findViewById(R.id.tvFps)
        spnModel = findViewById(R.id.spnModel)
        spnDevice = findViewById(R.id.spnDevice)
        framechecker = findViewById(R.id.checkFrame)
        spnTracker = findViewById(R.id.spnTracker)
        vTrackerOption = findViewById(R.id.vTrackerOption)
        surfaceView = findViewById(R.id.surfaceView)
        tvClassificationValue1 = findViewById(R.id.tvClassificationValue1)
        tvClassificationValue2 = findViewById(R.id.tvClassificationValue2)
        tvClassificationValue3 = findViewById(R.id.tvClassificationValue3)
        swClassification = findViewById(R.id.swPoseClassification)
        vClassificationOption = findViewById(R.id.vClassificationOption)
        initSpinner()
        breakTimeInt = intent.getIntExtra("breakTimeInt", 15)
        spnModel.setSelection(modelPos)
        swClassification.setOnCheckedChangeListener(setClassificationListener)
        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());
        if (!isCameraPermissionGranted()) {
            requestPermission()
        }
    }
    override fun onStart() {
        super.onStart()
        openCamera()
    }

    override fun onResume() {
        cameraSource?.resume()
        super.onResume()
    }

    override fun onPause() {
        cameraSource?.close()
        cameraSource = null
        super.onPause()
    }

    // check if permission is granted or not.
    private fun isCameraPermissionGranted(): Boolean {
        return checkPermission(
            Manifest.permission.CAMERA,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }

    // open camera
    private fun openCamera() {
        if (isCameraPermissionGranted()) {
            if (cameraSource == null) {
                cameraSource =
                    CameraSource(surfaceView, object : CameraSource.CameraSourceListener {
                        override fun onFPSListener(fps: Int) {
                            tvFPS.text = getString(R.string.tfe_pe_tv_fps, fps)
                        }

                        override fun onTimerListener(min: Int, sec: Int){
                            Timer.text = getString(R.string.tfe_pe_timer, min, sec)
                        }

                        override fun onExcrciseListener(currentExcrcise: String, nextExcrcise: String){
                            currentExcrcise_view.text = getString(
                                R.string.tfe_pe_currentExcrcise, currentExcrcise)
                            nextExcrcise_view.text = getString(
                                R.string.tfe_pe_nextExcrcise, nextExcrcise)
                        }

                        override fun onExcrciseCountListener(currentReps: Int, currentSets: Int) {
                            Reps.text = getString(R.string.tfe_pe_currentReps, currentReps)
                            Sets.text = getString(
                                R.string.tfe_pe_currentSets, currentSets, trainingList[0].sets)
                        }

                        override fun onExcrciseFeedbackListener(currentFeedback: String){
                            runOnUiThread {
                                Feedback.text = getString(R.string.tfe_pe_Feedback, currentFeedback)
                                ttsSpeak(Feedback.text.toString())
                            }
                        }

                        override fun onExcrciseBreakTimeListner(flag: Boolean,
                                                                sec: Int,
                                                                btFlag: Boolean) {
                            runOnUiThread{
                                if(flag){
                                    if(btFlag){
                                        ttsSpeak("쉬는 시간입니다.")
                                    }
                                    breakTime.visibility = View.VISIBLE
                                    breakTime.text = getString(R.string.break_time_timer,
                                            sec.toString())
                                }
                                else{
                                    if(sec == 0){
                                        ttsSpeak("운동을 시작하세요.")
                                    }
                                    breakTime.visibility = View.INVISIBLE
                                }
                            }
                        }

                        override fun onFrameCheckListener(flag: Boolean) {
                            runOnUiThread{
                                if(flag){
                                    framechecker.visibility = View.VISIBLE
                                }
                                else{
                                    framechecker.visibility = View.INVISIBLE
                                }
                            }
                        }

                        override fun onExcrciseFinishListener(
                            trainingDataList: ArrayList<TrainingData>,
                            excrciseTimeList: ArrayList<Long>
                        ){
                            println("finish listener in")
                            this@TrainingAiCameraActivity.trainingDataList = trainingDataList
                            this@TrainingAiCameraActivity.excrciseTimeList = excrciseTimeList
                            runOnUiThread{
                                dialog = LoadingDialog(this@TrainingAiCameraActivity)
                                dialog.showDialog()
                            }
                            cameraSource?.close()
                            var train_results: Train_result = setTrainResult(staticTrainingList,
                                                           trainingDataList,
                                                           excrciseTimeList)
                            runOnUiThread{
                                PostTrainResultCall(
                                    this@TrainingAiCameraActivity).PostTrainResult(train_results)
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                openResultPage()
                                dialog.loadingDismiss()
                                finish()
                            }, 3000)
                        }

                        override fun onDetectedInfo(
                            personScore: Float?,
                            poseLabels: List<Pair<String, Float>>?
                        ) {
                            tvScore.text = getString(R.string.tfe_pe_tv_score, personScore ?: 0f)
                            poseLabels?.sortedByDescending { it.second }?.let {
                                tvClassificationValue1.text = getString(
                                    R.string.tfe_pe_tv_classification_value,
                                    convertPoseLabels(if (it.isNotEmpty()) it[0] else null)
                                )
                                tvClassificationValue2.text = getString(
                                    R.string.tfe_pe_tv_classification_value,
                                    convertPoseLabels(if (it.size >= 2) it[1] else null)
                                )
                                tvClassificationValue3.text = getString(
                                    R.string.tfe_pe_tv_classification_value,
                                    convertPoseLabels(if (it.size >= 3) it[2] else null)
                                )
                            }
                        }

                    }, trainingList).apply {
                        prepareCamera()
                        prepareTrainer(breakTimeInt)
                    }
                isPoseClassifier()
                lifecycleScope.launch(Dispatchers.Main) {
                    cameraSource?.initCamera()
                }
            }
            createPoseEstimator()
            startGoogleTTS()
        }
    }

    private fun setTrainResult(staticTrainingList: ArrayList<testmodel>,
                               trainingDataList: ArrayList<TrainingData>,
                               excrciseTimeList: ArrayList<Long>): Train_result {
        var scoreForIndex = 0
        var postResult: Train_result = Train_result(0, ArrayList())

        if(breakTimeInt == 0){
            breakTimeInt = 15
        }

        for(i in 0 until staticTrainingList.size){
            val excrcise = exerciseId[staticTrainingList[i].titles]
            val startTime = excrciseTimeList[2*i]
            val endTime = excrciseTimeList[2*i+1]
            val realTime = (endTime - startTime - breakTimeInt * staticTrainingList[i].sets)
            val reps = staticTrainingList[i].reps
            val sets = staticTrainingList[i].sets
            var avg_score: Double = 0.0
            var score_sum = 0.0
            val totalReps = staticTrainingList[i].reps * staticTrainingList[i].sets

            scoreForIndex += staticTrainingList[i].reps * staticTrainingList[i].sets

            for(j in 0 until scoreForIndex){
               val avg = trainingDataList[j].excrciseScore
               score_sum += avg
               avg_score = score_sum / totalReps
            }

            postResult.result.add(Records(excrcise!!.toLong(), startTime, endTime, realTime.toInt(),
                                          reps, sets, round(avg_score*100) / 100))
        }
        postResult.breaktime = breakTimeInt

        println("postResult.breaktime : ${postResult.breaktime}")
        println("postResult.result : ${postResult.result}")

        return postResult
    }

    private fun startGoogleTTS() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Toast.makeText(this, "SDK version is low", Toast.LENGTH_SHORT).show()
            return
        }

        tts = TextToSpeech(this){
            if(it == TextToSpeech.SUCCESS){
                val result = tts?.setLanguage(Locale.KOREAN)
            }
        }
    }

    private fun ttsSpeak(strTTS: String){
        tts?.speak(strTTS, TextToSpeech.QUEUE_ADD, null, null)
    }

    private fun openResultPage(){
        println("open result in")
        if(trainingList.isEmpty()){
            val intent = Intent(this, TrainingResultActivity::class.java)
            intent.putParcelableArrayListExtra("trainingDataList", trainingDataList)
            intent.putParcelableArrayListExtra("trainingList", staticTrainingList)
            intent.putExtra("excrciseTimeList", excrciseTimeList)
            startActivity(intent)
        }
    }

    private fun convertPoseLabels(pair: Pair<String, Float>?): String {
        if (pair == null) return "empty"
        return "${pair.first} (${String.format("%.2f", pair.second)})"
    }

    private fun isPoseClassifier() {
        cameraSource?.setClassifier(if (isClassifyPose) PoseClassifier.create(this) else null)
    }

    // Initialize spinners to let user select model/accelerator/tracker.
    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.tfe_pe_models_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spnModel.adapter = adapter
            spnModel.onItemSelectedListener = changeModelListener
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.tfe_pe_device_name, android.R.layout.simple_spinner_item
        ).also { adaper ->
            adaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnDevice.adapter = adaper
            spnDevice.onItemSelectedListener = changeDeviceListener
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.tfe_pe_tracker_array, android.R.layout.simple_spinner_item
        ).also { adaper ->
            adaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spnTracker.adapter = adaper
            spnTracker.onItemSelectedListener = changeTrackerListener
        }
    }

    // Change model when app is running
    private fun changeModel(position: Int) {
        if (modelPos == position) return
        modelPos = position
        createPoseEstimator()
    }

    // Change device (accelerator) type when app is running
    private fun changeDevice(position: Int) {
        val targetDevice = when (position) {
            0 -> Device.CPU
            1 -> Device.GPU
            else -> Device.NNAPI
        }
        if (device == targetDevice) return
        device = targetDevice
        createPoseEstimator()
    }

    // Change tracker for Movenet MultiPose model
    private fun changeTracker(position: Int) {
        cameraSource?.setTracker(
            when (position) {
                1 -> TrackerType.BOUNDING_BOX
                2 -> TrackerType.KEYPOINTS
                else -> TrackerType.OFF
            }
        )
    }

    private fun createPoseEstimator() {
        // For MoveNet MultiPose, hide score and disable pose classifier as the model returns
        // multiple Person instances.
        val poseDetector = when (modelPos) {
            0 -> {
                // MoveNet Lightning (SinglePose)
                showPoseClassifier(true)
                showDetectionScore(true)
                showTracker(false)
                trainingList[0].excrciseStartTime = System.currentTimeMillis()
                MoveNet.create(this, device, ModelType.Lightning, trainingList)
            }
            1 -> {
                // MoveNet Thunder (SinglePose)
                showPoseClassifier(true)
                showDetectionScore(true)
                showTracker(false)
                trainingList[0].excrciseStartTime = System.currentTimeMillis()
                MoveNet.create(this, device, ModelType.Thunder, trainingList)
            }
            2 -> {
                // MoveNet (Lightning) MultiPose
                showPoseClassifier(false)
                showDetectionScore(false)
                // Movenet MultiPose Dynamic does not support GPUDelegate
                if (device == Device.GPU) {
                    showToast(getString(R.string.tfe_pe_gpu_error))
                }
                showTracker(true)
                MoveNetMultiPose.create(
                    this,
                    device,
                    Type.Dynamic
                )
            }
            3 -> {
                // PoseNet (SinglePose)
                showPoseClassifier(true)
                showDetectionScore(true)
                showTracker(false)
                PoseNet.create(this, device, trainingList)
            }
            else -> {
                null
            }
        }
        poseDetector?.let { detector ->
            cameraSource?.setDetector(detector)
        }
    }

    // Show/hide the pose classification option.
    private fun showPoseClassifier(isVisible: Boolean) {
        vClassificationOption.visibility = if (isVisible) View.VISIBLE else View.GONE
        if (!isVisible) {
            swClassification.isChecked = false
        }
    }

    // Show/hide the detection score.
    private fun showDetectionScore(isVisible: Boolean) {
        tvScore.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    // Show/hide classification result.
    private fun showClassificationResult(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        tvClassificationValue1.visibility = visibility
        tvClassificationValue2.visibility = visibility
        tvClassificationValue3.visibility = visibility
    }

    // Show/hide the tracking options.
    private fun showTracker(isVisible: Boolean) {
        if (isVisible) {
            // Show tracker options and enable Bounding Box tracker.
            vTrackerOption.visibility = View.VISIBLE
            spnTracker.setSelection(1)
        } else {
            // Set tracker type to off and hide tracker option.
            vTrackerOption.visibility = View.GONE
            spnTracker.setSelection(0)
        }
    }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                // You can use the API that requires the permission.
                openCamera()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Shows an error message dialog.
     */
    class ErrorDialog : DialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(activity)
                .setMessage(requireArguments().getString(ARG_MESSAGE))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // do nothing
                }
                .create()

        companion object {

            @JvmStatic
            private val ARG_MESSAGE = "message"

            @JvmStatic
            fun newInstance(message: String): ErrorDialog = ErrorDialog().apply {
                arguments = Bundle().apply { putString(ARG_MESSAGE, message) }
            }
        }
    }
}
