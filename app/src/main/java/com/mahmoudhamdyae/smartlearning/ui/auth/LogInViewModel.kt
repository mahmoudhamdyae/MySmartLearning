package com.mahmoudhamdyae.smartlearning.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudhamdyae.smartlearning.R

class LogInViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private var _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private var _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    fun validateTextsSignUp(userName: String?, email: String?, password: String?): Boolean {
        return if (userName.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
            _error.value = R.string.log_in_sign_up_error
            false
        } else {
            true
        }
    }

    fun validateTextsLogIn(email: String?, password: String?): Boolean {
        return if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            _error.value = R.string.log_in_sign_up_error
            false
        } else {
            true
        }
    }

    fun signUp(userName: String?, email: String?, password: String?) {
        validateTextsSignUp(userName, email, password)

        _userName.value = userName!!
        _email.value = email!!
        _password.value = password!!
    }

    fun logIn(email: String?, password: String?) {
        validateTextsLogIn(email, password)

        _email.value = email!!
        _password.value = password!!
    }
}