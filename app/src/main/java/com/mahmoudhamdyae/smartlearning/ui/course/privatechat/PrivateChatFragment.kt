package com.mahmoudhamdyae.smartlearning.ui.course.privatechat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.databinding.FragmentPrivateChatBinding
import com.mahmoudhamdyae.smartlearning.ui.course.addstudent.StudentsAdapter
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher

class PrivateChatFragment: BaseFragment() {

    private lateinit var binding: FragmentPrivateChatBinding
    override val viewModel: PrivateChatViewModel by viewModels()

    private lateinit var course: Course
    private lateinit var user: User
    private lateinit var teacher: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivateChatBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserType()

        course = PrivateChatFragmentArgs.fromBundle(requireArguments()).course!!
        teacher = PrivateChatFragmentArgs.fromBundle(requireArguments()).teacher
        user = PrivateChatFragmentArgs.fromBundle(requireArguments()).user
        viewModel.isTeacher.observe(viewLifecycleOwner) {
            if (it != IsTeacher.TEACHER) {
                viewModel.getTeacher(teacher)
            }
        }
        viewModel.getListOfStudents(course.id)

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.usersList.layoutManager = GridLayoutManager(context, 1)
        binding.usersList.adapter = StudentsAdapter(StudentsAdapter.OnClickListener {
            findNavController().navigate(PrivateChatFragmentDirections.actionPrivateChatFragmentToChatFragment(course, false, user, it))
        })
    }
}