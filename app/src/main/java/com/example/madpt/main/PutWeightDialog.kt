package com.example.madpt.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.madpt.API.member.MemberWeight
import com.example.madpt.API.member.PostMemberWeightCall
import com.example.madpt.MainActivity
import com.example.madpt.databinding.PutWeightDialogBinding
import com.example.madpt.user
import java.lang.NumberFormatException

class PutWeightDialog(val finishApp: () -> Unit): DialogFragment() {
    private var _binding: PutWeightDialogBinding? = null
    private val binding get() = _binding!!

    var mainActivity: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PutWeightDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.saveGetWeightButton.setOnClickListener {
            try {
                val user_weight = MemberWeight(binding.getWeight.text.toString().toDouble())
                PostMemberWeightCall(requireContext()).PostMemberWeight(user_weight)
            } catch (e: NumberFormatException) {
                Toast.makeText(requireActivity(), "소수 첫째 자리까지만 입력해주시기 바랍니다.", Toast.LENGTH_LONG).show()
            }
            mainActivity!!.setFragment()
            dismiss()
        }
        binding.cancelGetWeihgtButton.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}