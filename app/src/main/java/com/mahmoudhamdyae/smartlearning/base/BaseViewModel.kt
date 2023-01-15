package com.mahmoudhamdyae.smartlearning.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.STATUS

/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
abstract class BaseViewModel: ViewModel() {

    protected val _isTeacher = MutableLiveData(IsTeacher.NOTSET)
    val isTeacher: LiveData<IsTeacher>
        get() = _isTeacher

    protected var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    protected val _status = MutableLiveData<STATUS>()
    val status: LiveData<STATUS>
        get() = _status

    fun setIsTeacher(isTeacher: IsTeacher) {
        _isTeacher.value = isTeacher
    }
}