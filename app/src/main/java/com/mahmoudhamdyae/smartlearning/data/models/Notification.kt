package com.mahmoudhamdyae.smartlearning.data.models

import java.text.SimpleDateFormat
import java.util.*

//@Parcelize
data class Notification(
    val text: String? = null,
    val from: String? = null,
    val to: String? = null,
    val read: Boolean = false,
    val time: String = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Date())
)