package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Question
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import kotlinx.coroutines.launch

class AddQuizViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    var quiz = Quiz()

    val num = MutableLiveData(1)
    val question = MutableLiveData<String>()
    val option1 = MutableLiveData<String>()
    val option2 = MutableLiveData<String>()
    val option3 = MutableLiveData<String>()
    val option4 = MutableLiveData<String>()
    val answer = MutableLiveData(0)

    private fun validateTexts(): Boolean {
        return !(question.value.isNullOrEmpty()
                || option1.value.isNullOrEmpty()
                || option2.value.isNullOrEmpty()
                || option3.value.isNullOrEmpty()
                || option4.value.isNullOrEmpty()
                || answer.value == 0)
    }

    fun validateAndAddQuestion() {
        if (validateTexts()) {
            addQuestion()
        } else {
            _toast.value = R.string.add_quiz_empty_fields_toast
        }
    }

    private fun addQuestion() {
        val questionInQuiz = Question(num.value, question.value, option1.value,
            option2.value, option3.value, option4.value, answer.value!!)
        quiz.questions?.add(questionInQuiz)
        _error.value = "${quiz.questions}"

        num.value = num.value?.plus(1)
        question.value = ""
        option1.value = ""
        option2.value = ""
        option3.value = ""
        option4.value = ""
        answer.value = 0
    }

    fun finish(courseId: String) {
        if (validateTexts()) {
            addQuestion()
        }
        viewModelScope.launch {
            repository.saveQuiz(courseId, quiz)
        }
    }

    fun setValueOfQuiz(quiz2: Quiz) {
        quiz = quiz2
    }
}

@Suppress("UNCHECKED_CAST")
class AddQuizViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddQuizViewModel(repository) as T)
}