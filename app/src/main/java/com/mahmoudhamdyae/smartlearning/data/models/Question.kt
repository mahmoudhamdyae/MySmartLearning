package com.mahmoudhamdyae.smartlearning.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val number: Int? = null,
    val question: String? = null,
    val option1: String? = null,
    val option2: String? = null,
    val option3: String? = null,
    val option4: String? = null,
    val answer: Int = 0
): Parcelable {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "number" to number,
            "question" to question,
            "option1" to option1,
            "option2" to option2,
            "option3" to option3,
            "option4" to option4,
            "answer" to answer
        )
    }
}