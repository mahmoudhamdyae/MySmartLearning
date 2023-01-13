package com.mahmoudhamdyae.smartlearning.data.models

import java.util.*

data class Course (
    val courseName: String = "",
    val year: String = "",
    val teacherName: String = "",
    val id: String = UUID.randomUUID().toString(),
    )