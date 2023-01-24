package com.mahmoudhamdyae.smartlearning.ui.course.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class ChatViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>>
        get() = _messages

    fun getListOfMessages() {
        _messages.value = listOf(Message("shhhh"))
    }
}

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (ChatViewModel(repository) as T)
}