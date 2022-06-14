package com.example.madpt.training

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.madpt.R

class BreakTimeSetDialog(context: Context, listen: SetBreakTime, breaktime: Int) {

    private val dialog = Dialog(context)
    private val OnSetBreakTime = listen
    private val breaktime = breaktime

    fun showDialog(){
        dialog.setContentView(R.layout.break_time_set_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val breakTime = dialog.findViewById<EditText>(R.id.breakTime)
        breakTime.setText(breaktime.toString())

        dialog.findViewById<Button>(R.id.btn_plus).setOnClickListener{
            breakTime.setText((breakTime.text.toString().toInt()+1).toString())
        }

        dialog.findViewById<Button>(R.id.btn_minus).setOnClickListener {
            if (breakTime.text.toString().toInt()==0){
                breakTime.setText("0")
            }
            else {
                breakTime.setText((breakTime.text.toString().toInt() - 1).toString())
            }
        }

        dialog.findViewById<Button>(R.id.confirm).setOnClickListener {
            OnSetBreakTime.SetBreak(breakTime.text.toString().toInt())
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }

    }
}