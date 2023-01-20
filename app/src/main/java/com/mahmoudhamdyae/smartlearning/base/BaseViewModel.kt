package com.mahmoudhamdyae.smartlearning.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
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

    protected val _uploadStatus = MutableLiveData<STATUS>()
    val uploadStatus: LiveData<STATUS>
        get() = this._uploadStatus

    fun setIsTeacher(isTeacher: IsTeacher) {
        _isTeacher.value = isTeacher
    }

    fun uploadOnCompleteListener(uploadTask: Task<Void>) {
        this._uploadStatus.value = STATUS.LOADING
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                this._uploadStatus.value = STATUS.DONE
            } else {
                this._uploadStatus.value = STATUS.ERROR
                _error.value = task.exception?.message
            }
        }
    }
}