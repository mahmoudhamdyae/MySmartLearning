package com.mahmoudhamdyae.smartlearning.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this

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
        if (imageUri != "null") {
            Glide.with(requireContext())
                .load(imageUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .fallback(R.drawable.default_image)
                        .error(R.drawable.ic_broken_image))
                .into(binding.profileImage)
        } else {
            binding.profileImage.setImageDrawable(getDrawable(requireContext(), R.drawable.default_image))
        }
    }
}