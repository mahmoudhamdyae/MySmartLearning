package com.mahmoudhamdyae.smartlearning.ui.course.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.databinding.FragmentChatBinding

class ChatFragment: BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    override val viewModel: ChatViewModel by viewModels()

    private lateinit var course: Course
    private var isGroup = true
    private var anotherUser: User? = User()
    private var user: User? = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        course = ChatFragmentArgs.fromBundle(requireArguments()).course!!
        isGroup = ChatFragmentArgs.fromBundle(requireArguments()).isGroup
        user = ChatFragmentArgs.fromBundle(requireArguments()).user
        if (!isGroup) {
            anotherUser = ChatFragmentArgs.fromBundle(requireArguments()).anotherUser
        }

        if (isGroup) {
            binding.toolbar.title = course.courseName
            viewModel.getListOfGroupMessages(course.id)
        } else {
            binding.toolbar.title = anotherUser!!.userName
            viewModel.getListOfPrivateMessages(user!!, anotherUser!!)
        }

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.messageList.layoutManager = GridLayoutManager(context, 1)
        binding.messageList.adapter = ChatAdapter(ChatAdapter.OnClickListener {
        }, user!!.id)

        binding.sendFab.setOnClickListener {
            viewModel.sendMessage(isGroup, course.id, user!!, anotherUser!!)
        }
    }
}