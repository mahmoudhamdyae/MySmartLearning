package com.mahmoudhamdyae.smartlearning.ui.course.privatechat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.databinding.FragmentPrivateChatBinding

class PrivateChatFragment: Fragment() {

    private lateinit var binding: FragmentPrivateChatBinding
    private lateinit var viewModel: PrivateChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivateChatBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[PrivateChatViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }
}