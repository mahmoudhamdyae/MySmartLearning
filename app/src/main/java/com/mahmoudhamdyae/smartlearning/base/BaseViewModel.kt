package com.mahmoudhamdyae.smartlearning.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS

/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    protected var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    protected val _status = MutableLiveData<STATUS>()
    val status: LiveData<STATUS>
        get() = _status

    private val repository = FirebaseRepository()

    protected fun getUserData() {
        _user = repository.getUserData()
    }
}