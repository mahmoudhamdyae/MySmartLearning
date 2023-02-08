package com.mahmoudhamdyae.smartlearning.data.models

import java.text.SimpleDateFormat
import java.util.*

data class Material(
    val name: String? = null,
    val id: String =
        SimpleDateFormat("yyy-MM-dd hh:mm:ss", Locale.getDefault()).format(Date())
)