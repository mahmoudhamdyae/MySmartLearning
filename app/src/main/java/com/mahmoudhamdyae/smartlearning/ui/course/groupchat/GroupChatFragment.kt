package com.mahmoudhamdyae.smartlearning.ui.course.groupchat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentGroupChatBinding

class GroupChatFragment: BaseFragment() {

    private lateinit var binding: FragmentGroupChatBinding
    override lateinit var viewModel: GroupChatViewModel

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