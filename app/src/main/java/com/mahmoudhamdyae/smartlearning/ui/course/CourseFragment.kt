package com.mahmoudhamdyae.smartlearning.ui.course

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.databinding.FragmentCourseBinding
import com.mahmoudhamdyae.smartlearning.utils.Constants
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher

class CourseFragment: Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var viewModel: CourseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[CourseViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseId = CourseFragmentArgs.fromBundle(requireArguments()).courseId

        getUserType()

        viewModel.isTeacher.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
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
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToGroupChatFragment(courseId))
        }

        binding.privateChatCard.setOnClickListener {
            findNavController().navigate(CourseFragmentDirections.actionCourseFragmentToPrivateChatFragment(courseId))
        }
    }

    private fun getUserType() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val isTeacher = sharedPref.getBoolean(Constants.ISTEACHER, false)
        viewModel.setIsTeacher(if (isTeacher) IsTeacher.TEACHER else IsTeacher.STUDENT)
    }
}