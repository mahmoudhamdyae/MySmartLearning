package com.mahmoudhamdyae.smartlearning.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Notification
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class NotificationViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>>
        get() = _notifications

    init {
        getListOfNotifications()
    }

    private fun getListOfNotifications() {
        _notifications.value = mutableListOf(
            Notification(text = "textNotification")
        )
    }

    fun setNotificationRead() {
    }
}

@Suppress("UNCHECKED_CAST")
class NotificationViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (NotificationViewModel(repository) as T)
}