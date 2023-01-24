package com.mahmoudhamdyae.smartlearning.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.R
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

        binding.toolbar.inflateMenu(R.menu.menu_details)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseId = CourseFragmentArgs.fromBundle(requireArguments()).courseId

        getUserType()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

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
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToChatFragment(courseId, true))
        }

        binding.privateChatCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToPrivateChatFragment(courseId))
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.course_details -> {
                    createDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(requireContext(), R.style.Theme_SmartLearning).create()
        val dialogView = layoutInflater.inflate(R.layout.course_details_dialog, null)
        builder.setView(dialogView)

        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }
}