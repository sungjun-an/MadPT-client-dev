package com.example.madpt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class testmodel(
    var titles: String,
    var images: Int,
    var sets: Int = 0,
    var reps: Int = 0,
) :Parcelable
