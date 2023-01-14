package com.mahmoudhamdyae.smartlearning.ui.auth

import android.app.Application
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.ui.auth.IsTeacher.NOTSET
import com.mahmoudhamdyae.smartlearning.ui.auth.IsTeacher.TEACHER
import com.mahmoudhamdyae.smartlearning.utils.STATUS

enum class IsTeacher {
    TEACHER, STUDENT, NOTSET
}

class LogInViewModel(application: Application) : BaseViewModel(application) {

    // EditTexts fields
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isTeacher = MutableLiveData(NOTSET)

    val imageUri = MutableLiveData<String?>()

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean>
        get() = _navigate

    private val repository = FirebaseRepository()

    private fun validateTextsSignUp(): Boolean {
        return if (userName.value.isNullOrEmpty() || email.value.isNullOrEmpty() || password.value.isNullOrEmpty() || isTeacher.value == NOTSET) {
            _error.value = "Field Can\'t be empty"
            false
        } else {
            true
        }
    }

    private fun validateTextsLogIn(): Boolean {
        return if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            _error.value = "Field Can\'t be empty"
            false
        } else {
            true
        }
    }

    fun signUp() {
        if (validateTextsSignUp()) {

            _status.value = STATUS.LOADING
            repository.signUp(email.value!!, password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign up success.
                        Log.d("SignUp", "createUserWithEmail:success")
                        saveUserInDatabase()
                        saveProfilePicture()
                        navigate()
                        _status.value = STATUS.DONE
                    } else {
                        // Sign up fails
                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                        _status.value = STATUS.ERROR
                    }
                }
        }
    }

    fun logIn() {
        if (validateTextsLogIn()) {

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
                        _status.value = STATUS.ERROR
                    }
                }
        }
    }

    private fun saveUserInDatabase() {
        val isTeacher = isTeacher.value == TEACHER

        val user = User(userName.value!!, email.value!!, imageUri.value, isTeacher, repository.getUid())
        repository.saveUserInDatabase(user)
            .addOnSuccessListener {
        }.addOnFailureListener {
                _error.value = it.message
            }
    }

    private fun saveProfilePicture() {
        if (imageUri.value != null) {
            _status.value = STATUS.LOADING
            repository.saveProfilePicture(imageUri.value!!.toUri())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _status.value = STATUS.DONE
                    } else {
                        _error.value = task.exception.toString()
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