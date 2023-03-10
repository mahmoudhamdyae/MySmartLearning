package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class QuizDetailsViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _students = MutableLiveData<List<User>>(mutableListOf())
    val students: LiveData<List<User>>
        get() = _students

    private val _hashMap = MutableLiveData<HashMap<User, Double>>()
    val hashMap: LiveData<HashMap<User, Double>>
        get() = _hashMap

    fun getStudents(courseId: String, quizId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getStudentsInQuiz(courseId, quizId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val studentsList: MutableList<User> = mutableListOf()
                    val hashMapList: HashMap<User, Double> = HashMap()
                    for (studentsId in dataSnapshot.children) {
                        val studentIdItem = studentsId.key
                        val studentDegreeItem = studentsId.getValue(Double::class.java)

                        repository.getUserById(studentIdItem!!).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val student = snapshot.getValue(User::class.java)!!
                                studentsList.add(student)
                                hashMapList[student] = studentDegreeItem!!
                                _hashMap.value = hashMapList
                                _students.value = studentsList
                            }

                            override fun onCancelled(error: DatabaseError) {
                                _status.value = STATUS.ERROR
                            }
                        })
                    }
                    _status.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    _status.value = STATUS.ERROR
                }
            })
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