package com.mahmoudhamdyae.smartlearning.ui.course.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentChatBinding
import com.mahmoudhamdyae.smartlearning.ui.course.materials.MaterialsFragmentArgs

class ChatFragment: BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    override val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(FirebaseRepository())
    }

    private lateinit var courseId: String

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

        courseId = ChatFragmentArgs.fromBundle(requireArguments()).courseId!!
        viewModel.getListOfMessages()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.messageList.layoutManager = GridLayoutManager(context, 1)
        binding.messageList.adapter = ChatAdapter(ChatAdapter.OnClickListener {
        })
    }
}