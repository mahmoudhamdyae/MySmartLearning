package com.mahmoudhamdyae.smartlearning.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Notification
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>>
        get() = _notifications

    init {
        getListOfNotifications()
    }

    private fun getListOfNotifications() {
        viewModelScope.launch {
        }
    }

    fun setNotificationRead() {
    }
}