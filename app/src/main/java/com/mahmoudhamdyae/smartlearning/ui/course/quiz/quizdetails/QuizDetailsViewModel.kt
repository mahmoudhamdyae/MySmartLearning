package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

import androidx.lifecycle.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import kotlinx.coroutines.launch

class QuizDetailsViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    fun getStudents(courseId: String, quizId: String) {
        viewModelScope.launch {
            val studentsList: MutableList<User> = mutableListOf()
            val studentItem = User("nameo", "emailo", null, false)
            studentsList.add(studentItem)
            
            _students.value = studentsList
        }
    }
}

@Suppress("UNCHECKED_CAST")
class QuizDetailsViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (QuizDetailsViewModel(repository) as T)
}