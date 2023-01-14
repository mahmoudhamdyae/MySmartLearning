package com.mahmoudhamdyae.smartlearning.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.utils.STATUS

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    protected var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    protected val _status = MutableLiveData<STATUS>()
    val status: LiveData<STATUS>
        get() = _status
}