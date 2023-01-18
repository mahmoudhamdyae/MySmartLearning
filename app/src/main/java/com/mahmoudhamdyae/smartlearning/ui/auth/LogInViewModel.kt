package com.mahmoudhamdyae.smartlearning.ui.auth

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
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
                _downloadStatus.value = STATUS.LOADING
                repository.signUp(email.value!!, password.value!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign up success.
                            Log.d("SignUp", "createUserWithEmail:success")
                            saveUserInDatabase()
                            saveProfilePicture()
                            navigate()
                            _downloadStatus.value = STATUS.DONE
                        } else {
                            // Sign up fails
                            Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                            _error.value = task.exception?.message.toString()
                            _downloadStatus.value = STATUS.ERROR
                        }
                    }
            }
        }
    }

    fun logIn() {
        if (validateTextsLogIn()) {

            viewModelScope.launch {
                _downloadStatus.value = STATUS.LOADING
                repository.logIn(email.value!!, password.value!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Log in success.
                            Log.d("LogIn", "signInWithEmail:success")
                            navigate()
                            _downloadStatus.value = STATUS.DONE
                        } else {
                            // Log in fails.
                            Log.w("LogIn", "signInWithEmail:failure", task.exception)
                            _error.value = task.exception?.message.toString()
                            _downloadStatus.value = STATUS.ERROR
                        }
                    }
            }
        }
    }

    private fun saveUserInDatabase() {
        viewModelScope.launch {
            val isTeacher = _isTeacher.value == IsTeacher.TEACHER

            val user = User(userName.value!!, email.value!!, imageUri.value, isTeacher, repository.getUid())
            repository.saveUserInDatabase(user)
                .addOnSuccessListener {
                }.addOnFailureListener {
                    _error.value = it.message
                }
        }
    }

    private fun saveProfilePicture() {
        viewModelScope.launch {
            if (imageUri.value != null) {
                _downloadStatus.value = STATUS.LOADING
                repository.saveProfilePicture(imageUri.value!!.toUri())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _downloadStatus.value = STATUS.DONE
                        } else {
                            _error.value = task.exception?.message.toString()
                            _downloadStatus.value = STATUS.ERROR
                        }
                    }
            }
        }
    }

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    fun getUserData() {
        try {
            viewModelScope.launch {
                _user = repository.getUserData()
            }
        } catch (_: Exception) {}
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