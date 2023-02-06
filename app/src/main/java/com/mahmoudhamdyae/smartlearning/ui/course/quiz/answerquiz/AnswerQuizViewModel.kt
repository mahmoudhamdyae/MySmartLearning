package com.mahmoudhamdyae.smartlearning.ui.course.quiz.answerquiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Question
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class AnswerQuizViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    var quiz = Quiz()

    private val _num = MutableLiveData(1)
    val num: LiveData<Int>
        get() = _num
    private val _noOfQuestions = MutableLiveData<Int>()
    val nuOfQuestions: LiveData<Int>
        get() = _noOfQuestions
    val question = MutableLiveData<String>()
    val option1 = MutableLiveData<String>()
    val option2 = MutableLiveData<String>()
    val option3 = MutableLiveData<String>()
    val option4 = MutableLiveData<String>()
    val answer = MutableLiveData(0)

    private val _navigateUp = MutableLiveData(false)
    val navigateUp: LiveData<Boolean>
        get() = _navigateUp

    private fun putValues(question2: Question) {
        question.value = question2.question!!
        option1.value = question2.option1!!
        option2.value = question2.option2!!
        option3.value = question2.option3!!
        option4.value = question2.option4!!
        answer.value = question2.answer
    }

    fun onAnswer(answer2: Int): Boolean {
        return answer.value == answer2
    }

    fun setNextQuestion() {
    }

    fun finishNavigating() {
        _navigateUp.value = false
    }

    fun setValueOfQuiz(quiz2: Quiz) {
        quiz = quiz2
        _num.value = 1
        _noOfQuestions.value = quiz2.questions.size
        putValues(quiz2.questions[0])
    }
}

@Suppress("UNCHECKED_CAST")
class AnswerQuizViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AnswerQuizViewModel(repository) as T)
}