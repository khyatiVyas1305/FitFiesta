package com.example.fitfiesta

import android.os.Parcel
import android.os.Parcelable

data class WorkoutListData(var exercise: String) : Parcelable{
    constructor(parcel: Parcel) : this(parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(exercise)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkoutListData> {
        override fun createFromParcel(parcel: Parcel): WorkoutListData {
            return WorkoutListData(parcel)
        }

        override fun newArray(size: Int): Array<WorkoutListData?> {
            return arrayOfNulls(size)
        }
    }

}
