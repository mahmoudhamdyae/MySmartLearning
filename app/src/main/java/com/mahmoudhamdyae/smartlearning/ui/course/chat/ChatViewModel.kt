package com.mahmoudhamdyae.smartlearning.ui.course.chat

import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

    fun getListOfGroupMessages(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getGroupChat(courseId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList: MutableList<Message> = mutableListOf()
                    for (message in snapshot.children) {
                        val messageItem = message.getValue(Message::class.java)
                        messagesList.add(messageItem!!)
                    }
                    _messages.value = messagesList
                    _status.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }

    fun getListOfPrivateMessages(user: User, anotherUser: User) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getPrivateChat(user, anotherUser).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList: MutableList<Message> = mutableListOf()
                    for (message in snapshot.children) {
                        val messageItem = message.getValue(Message::class.java)
                        messagesList.add(messageItem!!)
                    }
                    _messages.value = messagesList
                    _status.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }

    private fun validateText(): Boolean = !messageText.value.isNullOrEmpty()

    fun sendMessage(isGroup: Boolean, courseId: String, user: User, anotherUser: User) {
        if (validateText()) {
            viewModelScope.launch {
                val message = Message(messageText.value, user.userName, user.userId, anotherUser.userName)
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