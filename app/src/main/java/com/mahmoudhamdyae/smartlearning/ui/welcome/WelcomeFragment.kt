package com.mahmoudhamdyae.smartlearning.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.databinding.FragmentWelcomeBinding
import com.mahmoudhamdyae.smartlearning.utils.Constants

class WelcomeFragment: Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toLogIn.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
            changeFirstTime()
        }
    }

    private fun changeFirstTime() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putBoolean(Constants.FIRSTTIME, false)
            apply()
        }
    }
}