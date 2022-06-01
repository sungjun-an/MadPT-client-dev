package com.example.madpt.training
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.madpt.R

class SetDialog(context: Context, s: String, i: Int) {

    private val image = i
    private val titles = s
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun showDialog(sets: Int, reps: Int){
        dialog.setContentView(R.layout.set_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val set = dialog.findViewById<EditText>(R.id.editTextSets)
        set.setText(sets.toString())
        val rep = dialog.findViewById<EditText>(R.id.editTextReps)
        rep.setText(reps.toString())

        dialog.findViewById<TextView>(R.id.trainingTitle).text = titles
        dialog.findViewById<ImageView>(R.id.image1).setImageResource(image)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_set_plus).setOnClickListener {
            set.setText((set.text.toString().toInt() + 1).toString())
        }
        dialog.findViewById<Button>(R.id.btn_set_minus).setOnClickListener {
            if (set.text.toString().toInt()==1){
                set.setText("1")
            }
            else {
                set.setText((set.text.toString().toInt() - 1).toString())
            }
        }

        dialog.findViewById<Button>(R.id.btn_rep_plus).setOnClickListener {
            rep.setText((rep.text.toString().toInt() + 1).toString())
        }
        dialog.findViewById<Button>(R.id.btn_rep_minus).setOnClickListener {
            if (rep.text.toString().toInt()==1){
                rep.setText("1")
            }
            else {
                rep.setText((rep.text.toString().toInt() - 1).toString())
            }
        }

        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            onClickListener.onClicked(set.text.toString().toInt(), rep.text.toString().toInt(), image, titles)
            dialog.dismiss()
        }

    }

    fun setOnClickListener(listener: OnDialogClickListener){
        onClickListener = listener
    }

    interface OnDialogClickListener {
        fun onClicked(sets: Int, reps: Int, images: Int, itemTitles: String)
    }
}