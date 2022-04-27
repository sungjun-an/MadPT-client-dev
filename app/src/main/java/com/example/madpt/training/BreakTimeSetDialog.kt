package com.example.madpt.training

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.madpt.R

class BreakTimeSetDialog(context: Context, listen: SetBreakTime) {

    private val dialog = Dialog(context)
    private val OnSetBreakTime = listen

    fun showDialog(){
        dialog.setContentView(R.layout.break_time_set_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val breakTime = dialog.findViewById<EditText>(R.id.breakTime)
        breakTime.setText("0")

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