package com.mahmoudhamdyae.smartlearning.ui.notification

import androidx.lifecycle.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Notification
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
        }
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