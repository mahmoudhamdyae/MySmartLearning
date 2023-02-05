package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import kotlinx.coroutines.launch

class AddQuizViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private var quiz = Quiz()

    val question = MutableLiveData<String>()
    val answer1 = MutableLiveData<String>()
    val answer2 = MutableLiveData<String>()
    val answer3 = MutableLiveData<String>()
    val answer4 = MutableLiveData<String>()

    private fun validateTexts(): Boolean {
        return !(question.value.isNullOrEmpty()
                || answer1.value.isNullOrEmpty()
                || answer2.value.isNullOrEmpty()
                || answer3.value.isNullOrEmpty()
                || answer4.value.isNullOrEmpty())
    }

    fun validateAndAddQuestion() {
        if (validateTexts()) {
            addQuestion()
        } else {
            _toast.value = R.string.add_quiz_empty_fields_toast
        }
    }

    private fun addQuestion() {
        viewModelScope.launch {
        }
    }

    fun finish() {
        if (validateTexts()) {
            addQuestion()
        }
    }

    fun getQuiz(quiz2: Quiz) {
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