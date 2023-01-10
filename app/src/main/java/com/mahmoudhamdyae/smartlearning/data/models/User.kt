package com.mahmoudhamdyae.smartlearning.data.models

data class User (
    val userName: String,
    val email: String,
    val imageUri: String?,
    val isTeacher: Boolean,
    val userId: String
)