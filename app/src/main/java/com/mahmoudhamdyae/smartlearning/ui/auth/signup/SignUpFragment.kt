package com.mahmoudhamdyae.smartlearning.ui.auth.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentSignUpBinding
import com.mahmoudhamdyae.smartlearning.ui.auth.AuthViewModel
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher

private const val PICK_IMAGE = 1

@Suppress("DEPRECATION")
class SignUpFragment : BaseFragment() {

    private lateinit var binding: FragmentSignUpBinding
    override val viewModel: AuthViewModel by viewModels()

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.signUpButton.setOnClickListener {
            viewModel.imageUri.value = imageUri.toString()
            viewModel.signUp()
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.teacher_radio_button) {
                viewModel.setIsTeacher(IsTeacher.TEACHER)
            } else {
                viewModel.setIsTeacher(IsTeacher.STUDENT)
            }
        }

        viewModel.navigate.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToMainFragment())
                viewModel.finishNavigate()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == PICK_IMAGE) {
            imageUri = data.data
            binding.profileImage.setImageURI(imageUri)
        }
    }
}