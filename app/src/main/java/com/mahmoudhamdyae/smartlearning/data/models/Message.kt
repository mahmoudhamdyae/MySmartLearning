package com.mahmoudhamdyae.smartlearning.data.models

import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val message: String? = null,
    val fromUserName: String? = null,
    val fromUid: String? = null,
    val toUserName: String? = null,
    val time: String =
        SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault()).format(Date()),
    val id: String =
        SimpleDateFormat("yyy-MM-dd hh:mm:ss", Locale.getDefault()).format(Date())
)