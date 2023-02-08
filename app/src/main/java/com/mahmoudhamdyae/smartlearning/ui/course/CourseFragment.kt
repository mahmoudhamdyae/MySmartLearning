package com.mahmoudhamdyae.smartlearning.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.databinding.FragmentCourseBinding

class CourseFragment: Fragment() {

    private lateinit var binding: FragmentCourseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseBinding.inflate(inflater)

        binding.toolbar.inflateMenu(R.menu.menu_details)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val course = CourseFragmentArgs.fromBundle(requireArguments()).course
        val user = CourseFragmentArgs.fromBundle(requireArguments()).user

        if (!user.teacher) {
            binding.addStudentCard.visibility = View.GONE
        }

        binding.toolbar.title = course!!.courseName
        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.quizzesCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToQuizFragment(course, user))
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
        customAlertDialogView.findViewById<TextView>(R.id.course_name).text = course.courseName
        customAlertDialogView.findViewById<TextView>(R.id.teacher_name).text = course.teacher?.userName
        customAlertDialogView.findViewById<TextView>(R.id.year).text = course.year
        customAlertDialogView.findViewById<TextView>(R.id.number_of_students).text = course.studentsNo.toString()

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