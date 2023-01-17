package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class AddStudentViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    init {
        getListOfStudents()
    }

    fun addStudentToCourse(user: User) {
        repository.addStudentToCourse(user)
    }

    private fun getListOfStudents() {
        repository.getStudents()
        _students.value = mutableListOf(User("test", "t" , "t", false))
    }
}

@Suppress("UNCHECKED_CAST")
class AddStudentViewModelFactory (
    private val repository: FirebaseRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddStudentViewModel(repository) as T)
}