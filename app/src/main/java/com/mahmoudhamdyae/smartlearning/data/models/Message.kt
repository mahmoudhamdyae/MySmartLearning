package com.mahmoudhamdyae.smartlearning.data.models

import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val message: String? = null,
    val user: String? = null,
    val time: String? =
        SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Date())
)