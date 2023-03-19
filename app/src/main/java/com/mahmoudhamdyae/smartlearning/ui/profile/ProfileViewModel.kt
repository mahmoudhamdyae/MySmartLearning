package com.mahmoudhamdyae.smartlearning.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class ProfileViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private var _uri = MutableLiveData<Uri>()
    val uri: LiveData<Uri>
        get() = _uri

    fun getProfileImage() {
        repository.getProfilePicture(repository.getUid()) { task ->
            _uri.value = task.result
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (ProfileViewModel(repository) as T)
}