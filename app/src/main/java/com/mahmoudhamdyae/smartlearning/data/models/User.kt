package com.mahmoudhamdyae.smartlearning.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val userName: String? = null,
    val email: String? = null,
    val imageUri: String? = null,
    val teacher: Boolean = false,
    val userId: String? = null
): Parcelable