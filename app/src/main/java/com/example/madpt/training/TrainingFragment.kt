package com.example.madpt.training

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.databinding.FragmentExcerciseBinding
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.TrainingAiCameraActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExcerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrainingFragment : Fragment(), OnRecyclerClickListener, OnRemove, SetBreakTime{

    private var trainList = arrayListOf<testmodel>()
    private var breakTime = 0

    override fun onClick(set: Int, rep: Int, image: Int, itemTitle: String) {
        trainList.add(testmodel(itemTitle,image,set,rep))
        binding.trainListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun OnRemoveClick(position: Int) {
        trainList.removeAt(position)
    }

    override fun SetBreak(time: Int) {
        Toast.makeText(context,"${time}",Toast.LENGTH_LONG).show()
        breakTime = time
    }

    private var _binding: FragmentExcerciseBinding? = null
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
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
            val dialog = BreakTimeSetDialog(requireContext(), this)
            dialog.showDialog()
        }
        // Inflate the layout for this fragment

        binding.btnCamera.setOnClickListener {
            val intent = Intent(requireContext(), TrainingAiCameraActivity::class.java)// 액티비티 이만 병경하셈
            intent.putExtra("trainList", trainList)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = TrainingAdapter(requireContext(), this)

        binding.trainListRecyclerView.adapter = TrainingList(trainList, this)
        binding.trainListRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExcerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrainingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}