package com.mahmoudhamdyae.smartlearning.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
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

        val course = CourseFragmentArgs.fromBundle(requireArguments()).course
        val user = CourseFragmentArgs.fromBundle(requireArguments()).user

        getUserType()

        binding.toolbar.title = course!!.courseName
        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.quizzesCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToQuizFragment(course.id))
        }

        binding.addStudentCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToAddStudentFragment(course))
        }

        binding.materialsCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToMaterialsFragment(course.id))
        }

        binding.groupChatCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToChatFragment(course, true, user))
        }

        binding.privateChatCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToPrivateChatFragment(course, user, course.teacher!!))
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.course_details -> {
                    createDialog(course)
                    true
                }
                else -> false
            }
        }
    }

    private fun createDialog(course: Course) {
        val customAlertDialogView = LayoutInflater.from(context)
            .inflate(R.layout.course_details_dialog, null, false)

        MaterialAlertDialogBuilder(requireContext())
            .setView(customAlertDialogView)
            .setIcon(R.drawable.course_details)
            .setTitle(R.string.course_details_head)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}