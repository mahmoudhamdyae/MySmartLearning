package com.mahmoudhamdyae.smartlearning.ui.course.groupchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.databinding.FragmentGroupChatBinding

class GroupChatFragment: Fragment() {

    private lateinit var binding: FragmentGroupChatBinding
    private lateinit var viewModel: GroupChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupChatBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[GroupChatViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }
}