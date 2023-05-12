package com.mahmoudhamdyae.smartlearning.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmoudhamdyae.smartlearning.R
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

        binding.viewPager.adapter = WelcomeViewPageAdapter()
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == MAX_STEP - 1) {
                    binding.nextButton.text = getString(R.string.welcome_get_started_button)
                    binding.nextButton.contentDescription = getString(R.string.welcome_get_started_button)
                } else {
                    binding.nextButton.text = getString(R.string.welcome_next_button)
                    binding.nextButton.contentDescription = getString(R.string.welcome_next_button)
                }
            }
        })
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        binding.skipButton.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
            changeFirstTime()
        }

        binding.nextButton.setOnClickListener {
            if (binding.nextButton.text.toString() == getString(R.string.welcome_get_started_button)) {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
                changeFirstTime()
            } else {
                val current = (binding.viewPager.currentItem) + 1
                binding.viewPager.currentItem = current

                // Update next button
                if (current == MAX_STEP - 1) {
                    binding.nextButton.text = getString(R.string.welcome_get_started_button)
                    binding.nextButton.contentDescription = getString(R.string.welcome_get_started_button)
                } else {
                    binding.nextButton.text = getString(R.string.welcome_next_button)
                    binding.nextButton.contentDescription = getString(R.string.welcome_next_button)
                }
            }
        }
    }

    private fun changeFirstTime() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putBoolean(Constants.FIRST_TIME, false)
            apply()
        }
    }

    companion object {
        const val MAX_STEP = 3
    }
}