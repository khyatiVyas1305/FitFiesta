package com.example.fitfiesta

import android.os.Parcel
import android.os.Parcelable

data class WorkoutPlansData(var text: String, var image: Int): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(text)
        dest.writeInt(image)
    }

    companion object CREATOR : Parcelable.Creator<WorkoutPlansData> {
        override fun createFromParcel(parcel: Parcel): WorkoutPlansData {
            return WorkoutPlansData(parcel)
        }

        override fun newArray(size: Int): Array<WorkoutPlansData?> {
            return arrayOfNulls(size)
        }
    }

}
