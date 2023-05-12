package com.mahmoudhamdyae.smartlearning.ui.course.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    val messageText = MutableLiveData<String>()

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>>
        get() = _messages

    fun getListOfGroupMessages(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getGroupChat(courseId, { messages ->
                _messages.value = messages
                _status.value = STATUS.DONE
            }, { error ->
                if (error != null) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }

    fun getListOfPrivateMessages(user: User, anotherUser: User) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getPrivateChat(user, anotherUser, { messages ->
                _messages.value = messages
                _status.value = STATUS.DONE
            }) { error ->
                if (error != null) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            }
        }
    }

    private fun validateText(): Boolean = !messageText.value.isNullOrEmpty()

    fun sendMessage(isGroup: Boolean, courseId: String, user: User, anotherUser: User) {
        if (validateText()) {
            viewModelScope.launch {
                val message = Message(messageText.value, user.userName, user.id, anotherUser.userName)
                _status.value = STATUS.LOADING
                if (isGroup) {
                    repository.sendMessageGroup(courseId, message) { error ->
                        if (error == null) {
                            _status.value = STATUS.DONE
                        } else {
                            _status.value = STATUS.ERROR
                            _error.value = error.message.toString()
                        }
                    }
                } else {
                    sendMessagePrivate(user, anotherUser, message)
                    sendMessagePrivate(anotherUser, user, message)
                }
                _status.value = STATUS.DONE
                messageText.value = ""
            }
        } else {
            _error.value = "Message Can\'t be empty"
        }
    }

    private fun sendMessagePrivate(user: User, anotherUser: User, message: Message) {
        repository.sendMessagePrivate(user, anotherUser, message) { error ->
            if (error == null) {
                _status.value = STATUS.DONE
            } else {
                _status.value = STATUS.ERROR
                _error.value = error.message.toString()
            }
        }
    }
}