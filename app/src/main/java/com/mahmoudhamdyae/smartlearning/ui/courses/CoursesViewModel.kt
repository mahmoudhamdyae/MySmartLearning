package com.mahmoudhamdyae.smartlearning.ui.courses

import androidx.lifecycle.*
import com.google.firebase.database.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class CoursesViewModel(private val repository: FirebaseRepository) : BaseViewModel() {

    val courseName = MutableLiveData<String>()

    val courses = MutableLiveData<MutableList<Course?>>(mutableListOf())

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    init {
        courses.value?.add(Course("name1"))
        courses.value?.add(Course("name2"))
    }

    fun getUserData() {
        try {
            viewModelScope.launch {
                _user = repository.getUserData()
            }
        } catch (_: Exception) {}
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.addCourse(course).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = STATUS.DONE
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception?.message
                }
            }
        }
    }

    fun getListOfCourses() {
        try {
            viewModelScope.launch {
//                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
//                val userDatabaseReference: DatabaseReference =
//                    FirebaseDatabase.getInstance().reference.child(Constants.USERS)
//
//                userDatabaseReference.child(mAuth.currentUser!!.uid).child(Constants.COURSES)
//                    .addValueEventListener(object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                        courses.value!!.clear()
//                        for (course in dataSnapshot.children) {
//                            courses.value!!.add(course.getValue(Course::class.java))
//                        }
//                    }
//                    override fun onCancelled(databaseError: DatabaseError) {
//                        // Getting Post failed, log a message
//                        Log.w("getCourses:onCancelled", "loadPost:onCancelled", databaseError.toException())
//                    }
//                })
            }
        } catch (e: Exception) {
            _error.value = e.message
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CoursesViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoursesViewModel(repository) as T)
}