package com.mahmoudhamdyae.smartlearning.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Quiz(
    val name: String? = null,
    val questions: MutableList<Question> = mutableListOf(),
    val date: String =
        SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Date()),
    val id: String =
        SimpleDateFormat("yyy-MM-dd hh:mm:ss", Locale.getDefault()).format(Date())
): Parcelable