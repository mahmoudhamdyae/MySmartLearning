package com.mahmoudhamdyae.smartlearning.data.models

import java.util.UUID

data class Quiz(
    val number: Int? = null,
    val date: String? = null,
    val id: String? = UUID.randomUUID().toString()
)