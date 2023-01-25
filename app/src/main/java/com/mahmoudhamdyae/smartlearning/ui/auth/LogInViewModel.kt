package com.mahmoudhamdyae.smartlearning.ui.auth

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class LogInViewModel(private val repository: FirebaseRepository) : BaseViewModel() {

    // EditTexts fields
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val imageUri = MutableLiveData<String?>()

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean>
        get() = _navigate

    private fun validateTextsSignUp(): Boolean {
        return if (userName.value.isNullOrEmpty()) {
            _error.value = "User Name Can\'t be empty"
            false
        } else if (email.value.isNullOrEmpty()) {
            _error.value = "Email Can\'t be empty"
            false
        } else if (password.value.isNullOrEmpty()) {
            _error.value = "Password Can\'t be empty"
            false
        }  else if (_isTeacher.value == IsTeacher.NOTSET) {
            _error.value = "Please, choose your account type"
            false
        } else {
            true
        }
    }

    private fun validateTextsLogIn(): Boolean {
        return if (email.value.isNullOrEmpty()) {
            _error.value = "Email Can\'t be empty"
            false
        } else if (password.value.isNullOrEmpty()) {
            _error.value = "Password Can\'t be empty"
            false
        } else {
            true
        }
    }

    fun signUp() {
        if (validateTextsSignUp()) {

            viewModelScope.launch {
                _status.value = STATUS.LOADING

                repository.getAllUsers().addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var userNameRepeated = false

                        for (user in snapshot.children) {
                            val userItem = user.getValue(User::class.java)
                            if (userName.value == userItem?.userName) {
                                userNameRepeated = true
                                _error.value = "Choose another user name"
                                _status.value = STATUS.ERROR
                            break
                            }
                        }

                        if (!userNameRepeated) {
                            repository.signUp(email.value!!, password.value!!)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Sign up success.
                                        Log.d("SignUp", "createUserWithEmail:success")
                                        saveUserInDatabase()
                                        saveProfilePicture()
                                        navigate()
                                    } else {
                                        // Sign up fails
                                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                                        _error.value = task.exception?.message.toString()
                                        _status.value = STATUS.ERROR
                                    }
                                }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("getUsers:Cancelled", "loadUsers:onCancelled", error.toException())
                        _status.value = STATUS.ERROR
                    }
                })
            }
        }
    }

    fun logIn() {
        if (validateTextsLogIn()) {

            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.logIn(email.value!!, password.value!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Log in success.
                            Log.d("LogIn", "signInWithEmail:success")
                            navigate()
                            _status.value = STATUS.DONE
                        } else {
                            // Log in fails.
                            Log.w("LogIn", "signInWithEmail:failure", task.exception)
                            _error.value = task.exception?.message.toString()
                            _status.value = STATUS.ERROR
                        }
                    }
            }
        }
    }

    private fun saveUserInDatabase() {
        val isTeacher = _isTeacher.value == IsTeacher.TEACHER
        val user = User(userName.value!!, email.value!!, imageUri.value, isTeacher, repository.getUid())

        onCompleteListener(repository.saveUserInDatabase(user))
    }

    private fun saveProfilePicture() {
        if (imageUri.value != null) {
            _status.value = STATUS.LOADING
            repository.saveProfilePicture(imageUri.value!!.toUri())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _status.value = STATUS.DONE
                    } else {
                        _error.value = task.exception?.message.toString()
                        _status.value = STATUS.ERROR
                    }
                }
        }
    }

    private fun navigate() {
        _navigate.value = true
    }

    fun finishNavigate() {
        _navigate.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class LogInViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (LogInViewModel(repository) as T)
}