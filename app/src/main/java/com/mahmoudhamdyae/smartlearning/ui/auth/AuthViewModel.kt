package com.mahmoudhamdyae.smartlearning.ui.auth

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    // EditTexts fields
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    var imageUri = MutableLiveData<String?>()

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean>
        get() = _navigate

    private fun validateTextsSignUp(): Boolean {
        return if (userName.value.isNullOrEmpty()) {
            _toast.value = R.string.auth_user_name_empty_toast
            false
        } else if (email.value.isNullOrEmpty()) {
            _toast.value = R.string.auth_email_empty_toast
            false
        } else if (password.value.isNullOrEmpty()) {
            _toast.value = R.string.auth_password_empty_toast
            false
        }  else if (_isTeacher.value == IsTeacher.NOT_SET) {
            _toast.value = R.string.auth_type_not_chosen_text_toast
            false
        } else {
            true
        }
    }

    private fun validateTextsLogIn(): Boolean {
        return if (email.value.isNullOrEmpty()) {
            _toast.value = R.string.auth_email_empty_toast
            false
        } else if (password.value.isNullOrEmpty()) {
            _toast.value = R.string.auth_password_empty_toast
            false
        } else {
            true
        }
    }

    fun signUp() {
        if (validateTextsSignUp()) {

            viewModelScope.launch {
                _status.value = STATUS.LOADING

                repository.getAllUsers({}, { error ->
                    if (error != null) {
                        _status.value = STATUS.LOADING
                        _error.value = error.message
                    }
                })
                repository.getAllUsers({ users ->
                    var userNameRepeated = false

                    for (userItem in users) {
                        if (userName.value == userItem.userName) {
                            userNameRepeated = true
                            _toast.value = R.string.sign_up_choose_another_user_name_toast
                            _status.value = STATUS.ERROR
                            break
                        }
                    }

                    if (!userNameRepeated) {
                        repository.signUp(email.value!!, password.value!!) { error ->
                            if (error == null) {
                                // Sign up success.
                                Log.d("SignUp", "createUserWithEmail:success")
                                saveProfilePicture()
                                saveUserInDatabase()
                                navigate()
                            } else {
                                // Sign up fails
                                Log.w("SignUp", "createUserWithEmail:failure", error)
                                _status.value = STATUS.ERROR
                                _error.value = error.message.toString()
                            }
                        }
                    }
                }, { error ->
                    if (error != null) {
                        _status.value = STATUS.ERROR
                        _error.value = error.message
                    }
                })
            }
        }
    }

    fun logIn() {
        if (validateTextsLogIn()) {

            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.logIn(email.value!!, password.value!!) { error ->
                    if (error == null) {
                        // Log in success.
                        Log.d("LogIn", "signInWithEmail:success")
                        navigate()
                        _status.value = STATUS.DONE
                    } else {
                        // Log in fails.
                        Log.w("LogIn", "signInWithEmail:failure", error)
                        _error.value = error.message.toString()
                        _status.value = STATUS.ERROR
                    }
                }
            }
        }
    }

    private fun saveUserInDatabase() {
        val isTeacher = _isTeacher.value == IsTeacher.TEACHER
        val user = User(userName.value!!, email.value!!, imageUri.value, isTeacher, repository.getUid())

        _status.value = STATUS.LOADING
        repository.saveUserInDatabase(user) { error ->
            if (error == null) {
                _status.value = STATUS.DONE
            } else {
                _status.value = STATUS.ERROR
                _error.value = error.message.toString()
            }
        }
    }

    private fun saveProfilePicture() {
        if (imageUri.value != null) {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                imageUri.value = repository.saveProfilePicture(imageUri.value!!.toUri()) { error ->
                    if (error == null) {
                        _status.value = STATUS.DONE
                    } else {
                        _status.value = STATUS.ERROR
                        _error.value = error.message.toString()
                    }
                }.toString()
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