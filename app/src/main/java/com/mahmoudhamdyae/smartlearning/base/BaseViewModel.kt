package com.mahmoudhamdyae.smartlearning.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(
): ViewModel() {

    protected val _isTeacher = MutableLiveData(IsTeacher.NOT_SET)
    val isTeacher: LiveData<IsTeacher>
        get() = _isTeacher

    protected var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    protected var _toast = MutableLiveData<Int>()
    val toast: LiveData<Int>
        get() = _toast

    protected val _status = MutableLiveData<STATUS>()
    val status: LiveData<STATUS>
        get() = this._status

    fun setIsTeacher(isTeacher: IsTeacher) {
        _isTeacher.value = isTeacher
    }

    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    viewModelScope.launch {
                        _error.value = throwable.message ?: "Unknown Error"
                    }
                }
//                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}