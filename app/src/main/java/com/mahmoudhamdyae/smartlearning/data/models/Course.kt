package com.mahmoudhamdyae.smartlearning.data.models

import java.util.*
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat

@Parcelize
data class Course (
    val courseName: String? = null,
    val year: String? = null,
    val teacher: User? = null,
    var studentsNo: Int = 0,
    val id: String =
        SimpleDateFormat("yyy-MM-dd hh:mm:ss", Locale.getDefault()).format(Date())
): Parcelable