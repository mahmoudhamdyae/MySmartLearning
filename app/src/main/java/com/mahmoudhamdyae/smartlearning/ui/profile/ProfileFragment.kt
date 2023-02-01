package com.mahmoudhamdyae.smartlearning.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentProfileBinding

class ProfileFragment: BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    override val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(FirebaseRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val user = ProfileFragmentArgs.fromBundle(requireArguments()).user

        binding.userName.text = user.userName
        binding.email.text = user.email
        binding.accountType.text = if (user.teacher) getString(R.string.account_type_teacher)
                                    else getString(R.string.account_type_student)

        val imageUri = user.imageUri
        if (!imageUri.isNullOrEmpty()) {
            viewModel.getProfileImage()
            viewModel.uri.observe(viewLifecycleOwner) {
                Glide.with(requireContext())
                    .load(it)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image))
                    .into(binding.profileImage)
            }
        }
    }
}