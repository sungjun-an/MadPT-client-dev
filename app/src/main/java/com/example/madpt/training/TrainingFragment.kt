package com.example.madpt.training

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.R
import com.example.madpt.databinding.FragmentExcerciseBinding
import com.example.madpt.storeTraining
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.TrainingAiCameraActivity
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TrainingFragment : Fragment(), OnRecyclerClickListener, OnRemove, SetBreakTime, Swaping, OnItemClickListener, FixExercise{

    private var trainList = arrayListOf<testmodel>()
    private var breakTime = 0
    private var storeTrainList= arrayListOf<storeTraining>()
    private val exerciseTitle = mapOf<Long, String>(
        1.toLong() to "PUSH UP",
        2.toLong() to "SQUAT",
        3.toLong() to "LUNGE",
        4.toLong() to "DUMBBELL",
        5.toLong() to "MOUNTAIN CLIMBING",
        6.toLong() to "SIDE LATERAL RAISE",
        7.toLong() to "SIDE LUNGE",
        8.toLong() to "DUMBEL CURL"

    )
    private val exerciseImage = mapOf<Long, Int>(
        1.toLong() to R.drawable.pushup,
        2.toLong() to R.drawable.standing,
        3.toLong() to R.drawable.lunge,
        4.toLong() to R.drawable.dumbell,
        5.toLong() to R.drawable.dumbell,
        6.toLong() to R.drawable.dumbell,
        7.toLong() to R.drawable.dumbell,
        8.toLong() to R.drawable.dumbell
    )

    override fun onClick(set: Int, rep: Int, image: Int, itemTitle: String) {
        trainList.add(testmodel(itemTitle,image,set,rep))
        binding.trainListRecyclerView.adapter?.notifyDataSetChanged()
        Log.d("YMC", "$trainList")
    }

    override fun OnRemoveClick(position: Int) {
        trainList.removeAt(position)
    }

    override fun SetBreak(time: Int) {
        breakTime = time
    }

    private var _binding: FragmentExcerciseBinding? = null
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExcerciseBinding.inflate(inflater, container, false)

        binding.btnBreakTime.setOnClickListener {
            val dialog = BreakTimeSetDialog(requireContext(), this, breakTime)
            dialog.showDialog()
        }
        binding.btnRoutineStore.setOnClickListener {
            val dialog = StoreTrain(requireContext(), breakTime)
            dialog.showDialog(trainList)
        }

        binding.btnCamera.setOnClickListener {
            val intent = Intent(requireContext(), TrainingAiCameraActivity::class.java)
            if(trainList.size == 0){
                Toast.makeText(activity, "실행할 운동 루틴이 없습니다.",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                intent.putParcelableArrayListExtra("trainList", trainList)
                intent.putExtra("breakTimeInt", breakTime)
                startActivity(intent)
            }
        }

        binding.btnRoutinLoading.setOnClickListener {
            val dialog = LodingTrainList(requireContext(), storeTrainList, this)
            dialog.showDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = TrainingAdapter(requireContext(), this)

        binding.trainListRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        binding.trainListRecyclerView.adapter = TrainingList(trainList, this, requireContext(), this)

        val recyclerViewAdapter = TrainingList(trainList, this, requireContext(), this)
        binding.trainListRecyclerView.adapter = recyclerViewAdapter

        val swipeHelperCallback = SwipeHelperCallback(recyclerViewAdapter)
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.trainListRecyclerView)

        binding.trainListRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrainingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun swapData(fromPos: Int, toPos: Int) {
        Collections.swap(trainList, fromPos, toPos)
        binding.trainListRecyclerView.adapter?.notifyItemMoved(fromPos, toPos)
    }

    override fun onClick(loadItem: PostTrainRoutine) {
        breakTime = loadItem.breaktime
        trainList.clear()
        for(i in loadItem.exercise_list){
            trainList.add(testmodel(exerciseTitle[i.exercise_id]!!, exerciseImage[i.exercise_id]!!, i.sets, i.reps))
        }
        binding.trainListRecyclerView.adapter?.notifyDataSetChanged()
        Log.d("YMC", "${loadItem.exercise_list}")
    }

    override fun fixEx(sets: Int, reps: Int, images: Int, itemTitles: String, position: Int) {
        trainList[position].sets = sets
        trainList[position].reps = reps
        Log.d("YMC", "$trainList")
    }
}