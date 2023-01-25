package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentAddStudentBinding

class AddStudentFragment: BaseFragment() {

    private lateinit var binding: FragmentAddStudentBinding
    override val viewModel: AddStudentViewModel by viewModels {
        AddStudentViewModelFactory(FirebaseRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStudentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val courseId = AddStudentFragmentArgs.fromBundle(requireArguments()).courseId
        viewModel.getListOfStudents(courseId!!)

        binding.studentsList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.studentsList.adapter = StudentsAdapter(StudentsAdapter.OnClickListener {
            // Add this student to the course
            viewModel.addStudentToCourse(it, courseId)
        })
    }
}