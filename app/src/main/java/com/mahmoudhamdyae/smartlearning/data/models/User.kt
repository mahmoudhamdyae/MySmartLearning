package com.mahmoudhamdyae.smartlearning.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class User (
    val userName: String? = null,
    val email: String? = null,
    val imageUri: String? = null,
    val teacher: Boolean = false,
    val id: String =
        SimpleDateFormat("yyy-MM-dd hh:mm:ss", Locale.getDefault()).format(Date())
): Parcelable