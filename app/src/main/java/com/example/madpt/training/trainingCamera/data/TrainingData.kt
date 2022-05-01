package com.example.madpt.training.trainingCamera.data

import android.os.Parcel
import android.os.Parcelable

data class TrainingData (
    var excrciseName: Int = -1,
    var excrciseCount: Int = -1,
    var excrciseScore : Int = -1
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(excrciseName)
        parcel.writeInt(excrciseCount)
        parcel.writeInt(excrciseScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrainingData> {
        override fun createFromParcel(parcel: Parcel): TrainingData {
            return TrainingData(parcel)
        }

        override fun newArray(size: Int): Array<TrainingData?> {
            return arrayOfNulls(size)
        }
    }
}