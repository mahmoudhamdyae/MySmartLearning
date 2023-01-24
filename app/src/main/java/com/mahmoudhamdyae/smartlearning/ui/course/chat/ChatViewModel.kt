package com.mahmoudhamdyae.smartlearning.ui.course.chat

import androidx.lifecycle.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    val messageText = MutableLiveData<String>()

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>>
        get() = _messages

    fun getListOfMessages() {
        _messages.value = listOf(Message("shhhh"))
    }

    private fun validateText(): Boolean = !messageText.value.isNullOrEmpty()

    fun sendMessage(isGroup: Boolean, courseId: String, user: User, anotherUser: User) {
        if (validateText()) {
            viewModelScope.launch {
                val message = Message(messageText.value, user.userName, anotherUser.userName)
                if (isGroup) {
                    onCompleteListener(repository.sendMessageGroup(courseId, message))
                } else {
                    onCompleteListener(repository.sendMessagePrivate(user, anotherUser, message))
                    onCompleteListener(repository.sendMessagePrivate(anotherUser, user, message))
                }
                _status.value = STATUS.DONE
                messageText.value = ""
            }
        } else {
            _error.value = "Message Can\'t be empty"
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (ChatViewModel(repository) as T)
}