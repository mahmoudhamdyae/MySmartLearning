package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.databinding.FragmentAddStudentBinding

class AddStudentFragment: Fragment() {

    private lateinit var binding: FragmentAddStudentBinding
    private lateinit var viewModel: AddStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[AddStudentViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }
}