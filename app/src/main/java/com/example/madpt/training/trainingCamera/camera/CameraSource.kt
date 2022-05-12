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

package com.example.madpt.training.trainingCamera.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.SurfaceView
import com.example.madpt.testmodel
import kotlinx.coroutines.suspendCancellableCoroutine
import com.example.madpt.training.trainingCamera.VisualizationUtils
import com.example.madpt.training.trainingCamera.YuvToRgbConverter
import com.example.madpt.training.trainingCamera.data.Person
import com.example.madpt.training.trainingCamera.data.TrainingData
import com.example.madpt.training.trainingCamera.ml.MoveNetMultiPose
import com.example.madpt.training.trainingCamera.ml.PoseClassifier
import com.example.madpt.training.trainingCamera.ml.PoseDetector
import com.example.madpt.training.trainingCamera.ml.TrackerType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.example.madpt.training.trainingCamera.algorithm.FeedBack

class CameraSource(
    private val surfaceView: SurfaceView,
    private val listener: CameraSourceListener? = null,
    private var trainingList : ArrayList<testmodel>
    ) {

    companion object {
        private const val PREVIEW_WIDTH = 1280
        private const val PREVIEW_HEIGHT = 720

        /** Threshold for confidence score. */
        private const val MIN_CONFIDENCE = .2f
        private const val TAG = "Camera Source"
    }

    private val lock = Any()
    private var detector: PoseDetector? = null
    private var classifier: PoseClassifier? = null
    private var isTrackerEnabled = false
    private var yuvConverter: YuvToRgbConverter = YuvToRgbConverter(surfaceView.context)
    private lateinit var imageBitmap: Bitmap

    /** Frame count that have been processed so far in an one second interval to calculate FPS. */
    private var fpsTimer: Timer? = null
    private var excrciseTimer: Timer? = null
    private var currentReps: Int = 0
    private var currentSets: Int = 0
    private var currentFeedback: Double = 0.0
    private var currentExcrcise: String = ""
    private var nextExcrcise: String = ""
    private var excrciseTimeList: ArrayList<Long> = ArrayList()
    private var time = 0
    private var min = 0
    private var sec = 0
    private var time_break = 5
    private var breakTimer = Timer()
    private var currentExcrciseDataList: ArrayList<Int> = ArrayList()
    private var frameProcessedInOneSecondInterval = 0
    private var framesPerSecond = 0
    private var feedBackCalculator: FeedBack = FeedBack()

    /** Detects, characterizes, and connects to a CameraDevice (used for all camera operations) */
    private val cameraManager: CameraManager by lazy {
        val context = surfaceView.context
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    /** Readers used as buffers for camera still shots */
    private var imageReader: ImageReader? = null

    /** The [CameraDevice] that will be opened in this fragment */
    private var camera: CameraDevice? = null

    /** Internal reference to the ongoing [CameraCaptureSession] configured with our parameters */
    private var session: CameraCaptureSession? = null

    /** [HandlerThread] where all buffer reading operations run */
    private var imageReaderThread: HandlerThread? = null

    /** [Handler] corresponding to [imageReaderThread] */
    private var imageReaderHandler: Handler? = null
    private var cameraId: String = ""

    suspend fun initCamera() {
        camera = openCamera(cameraManager, cameraId)
        imageReader =
            ImageReader.newInstance(PREVIEW_WIDTH, PREVIEW_HEIGHT, ImageFormat.YUV_420_888, 3)
        imageReader?.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage()
            if (image != null) {
                if (!::imageBitmap.isInitialized) {
                    imageBitmap =
                        Bitmap.createBitmap(
                            PREVIEW_WIDTH,
                            PREVIEW_HEIGHT,
                            Bitmap.Config.ARGB_8888
                        )
                }
                yuvConverter.yuvToRgb(image, imageBitmap)
                // Create rotated version for portrait display
                val rotateMatrix = Matrix()
                rotateMatrix.postRotate(270.0f)

                val rotatedBitmap = Bitmap.createBitmap(
                    imageBitmap, 0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT,
                    rotateMatrix, false
                )
                processImage(rotatedBitmap)
                image.close()
            }
        }, imageReaderHandler)

        imageReader?.surface?.let { surface ->
            session = createSession(listOf(surface))
            val cameraRequest = camera?.createCaptureRequest(
                CameraDevice.TEMPLATE_PREVIEW   
            )?.apply {
                addTarget(surface)
            }
            cameraRequest?.build()?.let {
                session?.setRepeatingRequest(it, null, null)
            }
        }
    }

    private suspend fun createSession(targets: List<Surface>): CameraCaptureSession =
        suspendCancellableCoroutine { cont ->
            camera?.createCaptureSession(targets, object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(captureSession: CameraCaptureSession) =
                    cont.resume(captureSession)

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    cont.resumeWithException(Exception("Session error"))
                }
            }, null)
        }

    @SuppressLint("MissingPermission")
    private suspend fun openCamera(manager: CameraManager, cameraId: String): CameraDevice =
        suspendCancellableCoroutine { cont ->
            manager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) = cont.resume(camera)

                override fun onDisconnected(camera: CameraDevice) {
                    camera.close()
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    if (cont.isActive) cont.resumeWithException(Exception("Camera error"))
                }
            }, imageReaderHandler)
        }

    fun prepareCamera() {
        for (cameraId in cameraManager.cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)

            // We don't use a front facing camera in this sample.
            val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
            if (cameraDirection != null &&
                cameraDirection == CameraCharacteristics.LENS_FACING_BACK
            ) {
                continue
            }
            this.cameraId = cameraId
        }
    }

    fun prepareTrainer(){
        startTimer()
    }

    fun setDetector(detector: PoseDetector) {
        synchronized(lock) {
            if (this.detector != null) {
                this.detector?.close()
                this.detector = null
            }
            this.detector = detector
        }
    }

    fun setClassifier(classifier: PoseClassifier?) {
        synchronized(lock) {
            if (this.classifier != null) {
                this.classifier?.close()
                this.classifier = null
            }
            this.classifier = classifier
        }
    }

    /**
     * Set Tracker for Movenet MuiltiPose model.
     */
    fun setTracker(trackerType: TrackerType) {
        isTrackerEnabled = trackerType != TrackerType.OFF
        (this.detector as? MoveNetMultiPose)?.setTracker(trackerType)
    }

    fun resume() {
        imageReaderThread = HandlerThread("imageReaderThread").apply { start() }
        imageReaderHandler = Handler(imageReaderThread!!.looper)
        fpsTimer = Timer()
        fpsTimer?.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    framesPerSecond = frameProcessedInOneSecondInterval
                    frameProcessedInOneSecondInterval = 0
                }
            },
            0,
            1000
        )
    }

    fun startTimer() {
        excrciseTimer = Timer()
        excrciseTimer = timer(period = 1000){
            time++
            min = time / 60
            sec = time % 60

            listener?.onTimerListener(min, sec)
        }
    }

    fun close() {
        session?.close()
        session = null
        camera?.close()
        camera = null
        imageReader?.close()
        imageReader = null
        detector?.close()
        detector = null
    }

    private var breakTimeFlag = false
    private var timeFlag = true

    // process image
    private fun processImage(bitmap: Bitmap) {
        val persons = mutableListOf<Person>()
        var classificationResult: List<Pair<String, Float>>? = null
        var dataList: ArrayList<Int>
        var feedback: ArrayList<Int>
        var p1: Pair<ArrayList<Int>, ArrayList<Int>>

        synchronized(lock) {
            if(currentReps % trainingList[0].reps == 0 && currentReps != 0 && breakTimeFlag){
                if(timeFlag){
                    timeFlag = false
                    showBreakTimeView()
                }
                else{
                    println("운동 중")
                }
            }
            else{
                detector?.estimatePoses(bitmap)?.let {
                    persons.addAll(it)
                    p1 = detector?.doExcrcise(persons)!!
                    dataList = p1.component1()
                    feedback = p1.component2()
                    if (dataList.isEmpty()){
                        println("운동 종료")
                        excrciseTimeList = detector?.getExcrciseTimeList()!!
                        for(i in 0 until excrciseTimeList.size){
                            val excrciseTimeTemp = excrciseTimeList[i]
                            println("$i : $excrciseTimeTemp")
                        }
                        val trainingDataList = detector?.getTrainingData()!!
                        println(trainingDataList)
                        finishExcrcise(trainingDataList, excrciseTimeList)
                    }
                    else{
                        showExcrciseView(dataList, feedback)
                    }

                    // if the model only returns one item, allow running the Pose classifier.
                    if (persons.isNotEmpty()) {
                        classifier?.run {
                            classificationResult = classify(persons[0])
                        }
                    }
                }
            }
        }
        frameProcessedInOneSecondInterval++
        if (frameProcessedInOneSecondInterval == 1) {
            // send fps to view
            listener?.onFPSListener(framesPerSecond)
        }

        // if the model returns only one item, show that item's score.
        if (persons.isNotEmpty()) {
            listener?.onDetectedInfo(persons[0].score, classificationResult)
        }
        visualize(persons, bitmap)
    }

    private fun finishExcrcise(trainingDataList: ArrayList<TrainingData>,
                               excrciseTimeList: ArrayList<Long>){
        println("finish Excrcise")
        listener?.onExcrciseFinishListener(trainingDataList, excrciseTimeList)
    }

    private fun showBreakTimeView(){
        breakTimer = Timer()
        breakTimer = timer(period = 1000) {
            time_break--

            if (time_break == 0){
                breakTimer.cancel()
                time_break = 5
                breakTimeFlag = false
                println("breakTimeFlag: $breakTimeFlag")
                listener?.onExcrciseBreakTimeListner(false, time_break % 60)
            }
            else{
                listener?.onExcrciseBreakTimeListner(true, time_break % 60)
            }
        }
    }

    private fun showExcrciseView(dataList: ArrayList<Int>, feedBack: ArrayList<Int>) {
        currentExcrcise = trainingList[0].titles
        var exerciseId: Int = -1
            nextExcrcise = if(trainingList.size != 1){
            trainingList[1].titles
        } else{
            "Empty"
        }


        currentReps = dataList[0]
        currentSets = dataList[1]
        if (feedBack.size > 0){
            exerciseId = feedBack.get(0)
            currentFeedback = ArrayList(feedBack.subList(1, feedBack.size)).average()
        }
        if (feedBack.size > 0){
            var feedBackMsg = feedBackCalculator.calculateFeedBack(exerciseId, ArrayList(feedBack.subList(1, feedBack.size)))
            listener?.onExcrciseFeedbackListener(feedBackMsg + currentFeedback)
        }

        listener?.onExcrciseCountListener(currentReps, currentSets)
        listener?.onExcrciseListener(currentExcrcise, nextExcrcise)

        if(currentReps % trainingList[0].reps == 0 && currentReps != 0 && timeFlag){
            breakTimeFlag = true
        }
        else if(currentReps % trainingList[0].reps != 0){
            println("in -> $currentReps")
            timeFlag = true
        }
    }

    private fun visualize(persons: List<Person>, bitmap: Bitmap) {

        val outputBitmap = VisualizationUtils.drawBodyKeypoints(
            bitmap,
            persons.filter { it.score > MIN_CONFIDENCE }, isTrackerEnabled
        )

        val holder = surfaceView.holder
        val surfaceCanvas = holder.lockCanvas()
        surfaceCanvas?.let { canvas ->
            val screenWidth: Int
            val screenHeight: Int
            val left: Int
            val top: Int

            if (canvas.height > canvas.width) {
                val ratio = outputBitmap.height.toFloat() / outputBitmap.width
                screenWidth = canvas.width
                left = 0
                screenHeight = (canvas.width * ratio).toInt()
                top = (canvas.height - screenHeight) / 2
            } else {
                val ratio = outputBitmap.width.toFloat() / outputBitmap.height
                screenHeight = canvas.height
                top = 0
                screenWidth = (canvas.height * ratio).toInt()
                left = (canvas.width - screenWidth) / 2
            }
            val right: Int = left + screenWidth
            val bottom: Int = top + screenHeight

            canvas.drawBitmap(
                outputBitmap, Rect(0, 0, outputBitmap.width, outputBitmap.height),
                Rect(left, top, right, bottom), null
            )
            surfaceView.holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun stopImageReaderThread() {
        imageReaderThread?.quitSafely()
        try {
            imageReaderThread?.join()
            imageReaderThread = null
            imageReaderHandler = null
        } catch (e: InterruptedException) {
            Log.d(TAG, e.message.toString())
        }
    }

    interface CameraSourceListener {
        fun onFPSListener(fps: Int)
        fun onTimerListener(min: Int, sec: Int)
        fun onExcrciseListener(currentExcrcise: String, nextExcrcise: String)
        fun onExcrciseCountListener(currentReps: Int, currentSets: Int)
        fun onExcrciseFeedbackListener(currentFeedback: String)
        fun onExcrciseFinishListener(
            trainingDataList: ArrayList<TrainingData>,
            excrciseTimeList: ArrayList<Long>
        )
        fun onExcrciseBreakTimeListner(flag: Boolean, sec: Int)
        fun onDetectedInfo(personScore: Float?, poseLabels: List<Pair<String, Float>>?)
    }
}
