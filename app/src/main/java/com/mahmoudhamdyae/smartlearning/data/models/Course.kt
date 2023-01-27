package com.mahmoudhamdyae.smartlearning.data.models

import java.util.*
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course (
    val courseName: String? = null,
    val year: String? = null,
    val teacherName: String? = null,
    val studentsNo: Int = 0,
    val id: String = UUID.randomUUID().toString()
): Parcelable