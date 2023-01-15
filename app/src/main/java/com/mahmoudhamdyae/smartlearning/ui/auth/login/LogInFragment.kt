package com.mahmoudhamdyae.smartlearning.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentLogInBinding
import com.mahmoudhamdyae.smartlearning.ui.auth.LogInViewModel

class LogInFragment : BaseFragment() {

    private lateinit var binding: FragmentLogInBinding
    override val viewModel: LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logInButton.setOnClickListener {
            viewModel.logIn()
        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }

        viewModel.navigate.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainFragment())
                viewModel.finishNavigate()
            }
        }
    }
}