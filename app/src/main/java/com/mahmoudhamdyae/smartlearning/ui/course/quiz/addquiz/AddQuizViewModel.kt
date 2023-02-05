package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

import androidx.lifecycle.*
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

    val num = MutableLiveData<Int>()
    val question = MutableLiveData<String>()
    val option1 = MutableLiveData<String>()
    val option2 = MutableLiveData<String>()
    val option3 = MutableLiveData<String>()
    val option4 = MutableLiveData<String>()
    val answer = MutableLiveData(0)

    private val _questionAdded = MutableLiveData(false)
    val questionAdded: LiveData<Boolean>
        get() = _questionAdded

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

        num.value = num.value?.plus(1)
        question.value = ""
        option1.value = ""
        option2.value = ""
        option3.value = ""
        option4.value = ""
        answer.value = 0

        _questionAdded.value = true
    }

    private fun modifyquestion() {
    }

    fun finish(courseId: String): Boolean {
        if (validateTexts()) {
            addQuestion()
        }
        return if (quiz.questions!!.isNotEmpty()) {
            viewModelScope.launch {
                onCompleteListener(repository.saveQuiz(courseId, quiz))
            }
            true
        } else {
            _toast.value = R.string.add_quiz_empty_questions_toast
            false
        }
    }

    fun setValueOfQuiz(quiz2: Quiz) {
        quiz = quiz2
    }

    fun addedQuestion() {
        _questionAdded.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class AddQuizViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddQuizViewModel(repository) as T)
}