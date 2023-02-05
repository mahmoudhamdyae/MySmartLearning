package com.mahmoudhamdyae.smartlearning.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.List

//@Parcelize
data class Quiz(
    val name: String? = null,
//    val questions: MutableList<Question>? = mutableListOf(),
    val date: String? =
        SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Date()),
    val id: String? = UUID.randomUUID().toString()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),

        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)

        parcel.writeString(date)
        parcel.writeString(id)
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }
}