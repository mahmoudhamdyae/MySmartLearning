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

    private val _num = MutableLiveData<Int>()
    val num: LiveData<Int>
        get() = _num
    val question = MutableLiveData<String>()
    val option1 = MutableLiveData<String>()
    val option2 = MutableLiveData<String>()
    val option3 = MutableLiveData<String>()
    val option4 = MutableLiveData<String>()
    val answer = MutableLiveData(0)

    private val _navigateUp = MutableLiveData(false)
    val navigateUp: LiveData<Boolean>
        get() = _navigateUp

    private val _visibilityOfModifyButton = MutableLiveData(true)
    val visibilityOfModifyButton: LiveData<Boolean>
        get() = _visibilityOfModifyButton

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
        val questionInQuiz = Question(_num.value, question.value, option1.value,
            option2.value, option3.value, option4.value, answer.value!!)
        quiz.questions.add(questionInQuiz)

        _num.value = _num.value?.plus(1)
        val question = Question(_num.value, "", "", "", "", "", 0)
        putValuesToEditTexts(question)
    }

    fun validateAndUpdateQuestion(courseId: String) {
        if (validateTexts()) {
            updateQuestion(courseId)
        } else {
            _toast.value = R.string.add_quiz_empty_fields_toast
        }
    }

    fun putValuesToEditTexts(question2: Question) {
        question.value = question2.question!!
        option1.value = question2.option1!!
        option2.value = question2.option2!!
        option3.value = question2.option3!!
        option4.value = question2.option4!!
        answer.value = question2.answer
    }

    private fun updateQuestion(courseId: String) {
        val questionInQuiz = Question(_num.value, question.value, option1.value,
            option2.value, option3.value, option4.value, answer.value!!)
        quiz.questions[_num.value!! - 1] = questionInQuiz
        viewModelScope.launch {
            onCompleteListener(repository.updateQuestion(courseId, quiz.id, _num.value!! - 1, questionInQuiz))
        }

        if (_num.value!! >= quiz.questions.size - 1) {
            _visibilityOfModifyButton.value = false
        }

        if (_num.value!! >= quiz.questions.size) {
            _navigateUp.value = true
        } else {
            _num.value = _num.value?.plus(1)
            putValuesToEditTexts(quiz.questions[_num.value!! - 1])
        }
    }

    fun finishAdd(courseId: String, addType: Int) {
        if (validateTexts()) {
            addQuestion()
        }
        if (quiz.questions.isNotEmpty()) {
            viewModelScope.launch {
                if (addType == 0) {
                    // Add Quiz
                    onCompleteListener(repository.saveQuiz(courseId, quiz))
                } else {
                    // Add Question
                    onCompleteListener(repository.addQuestions(courseId,quiz.id, quiz.questions))
                }
            }
            _navigateUp.value = true
        } else {
            _toast.value = R.string.add_quiz_empty_questions_toast
        }
    }

    fun finishUpdate(courseId: String) {
        if (validateTexts()) {
            updateQuestion(courseId)
        }
        _navigateUp.value = true
    }

    fun setNumValue(num2: Int) {
        _num.value = num2
    }

    fun finishNavigating() {
        _navigateUp.value = false
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