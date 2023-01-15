package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Quiz

class QuizViewModel : BaseViewModel() {

    private val _quizzes = MutableLiveData<List<Quiz>>()
    val quizzes: LiveData<List<Quiz>>
        get() = _quizzes

    init {
        val c = mutableListOf<Quiz>()
        c.add(Quiz(1, "1/1/1"))
        c.add(Quiz(2, "2/2/2"))
        c.add(Quiz(3, "3/3/3"))

        _quizzes.value = c
    }
}