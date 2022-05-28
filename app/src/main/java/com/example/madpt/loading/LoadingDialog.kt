package com.example.madpt.loading

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.madpt.R

class LoadingDialog(context: Context) {
    private val dialog = Dialog(context)
    private val context = context

    fun showDialog() {
        dialog.setContentView(R.layout.dialog_loading)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        Glide.with(context).load(R.raw.loading).into(dialog.findViewById<ImageView>(R.id.imageView2))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun loadingDismiss() {
        dialog.dismiss()
    }
}