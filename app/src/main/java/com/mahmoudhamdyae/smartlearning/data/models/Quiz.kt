package com.mahmoudhamdyae.smartlearning.data.models

import java.text.SimpleDateFormat
import java.util.*

data class Quiz(
    val name: String? = null,
    val date: String? =
        SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Date()),
    val id: String? = UUID.randomUUID().toString()
)