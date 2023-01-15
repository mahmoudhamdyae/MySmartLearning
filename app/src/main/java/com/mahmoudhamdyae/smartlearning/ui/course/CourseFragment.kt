package com.mahmoudhamdyae.smartlearning.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentCourseBinding

class CourseFragment: BaseFragment() {

    private lateinit var binding: FragmentCourseBinding
    override val viewModel: CourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseId = CourseFragmentArgs.fromBundle(requireArguments()).courseId

        getUserType()

        binding.quizzesCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToQuizFragment(courseId))
        }

        binding.addStudentCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToAddStudentFragment(courseId))
        }

        binding.materialsCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToMaterialsFragment(courseId))
        }

        binding.groupChatCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToGroupChatFragment(courseId))
        }

        binding.privateChatCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToPrivateChatFragment(courseId))
        }
    }
}