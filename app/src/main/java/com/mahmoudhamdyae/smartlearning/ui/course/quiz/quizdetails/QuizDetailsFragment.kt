package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizDetailsBinding
import com.mahmoudhamdyae.smartlearning.ui.course.addstudent.StudentsAdapter

class QuizDetailsFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizDetailsBinding
    override val viewModel: QuizDetailsViewModel by viewModels {
        QuizDetailsViewModelFactory(FirebaseRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quiz = QuizDetailsFragmentArgs.fromBundle(requireArguments()).quiz
        val course = QuizDetailsFragmentArgs.fromBundle(requireArguments()).course

        binding.toolbar.title = quiz.name
        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.getStudents(course.id, quiz.id)

        binding.quizModifyButton.setOnClickListener {
            findNavController().navigate(QuizDetailsFragmentDirections.actionQuizDetailsFragmentToAddQuizFragment(quiz, course.id, 1))
        }

        binding.addQuestionButton.setOnClickListener {
            findNavController().navigate(QuizDetailsFragmentDirections.actionQuizDetailsFragmentToAddQuizFragment(quiz, course.id, 2))
        }

        binding.studentsRecyclerView.layoutManager = GridLayoutManager(context, 1)
        binding.studentsRecyclerView.adapter = StudentsAdapter(StudentsAdapter.OnClickListener {
            findNavController().navigate(QuizDetailsFragmentDirections.actionQuizDetailsFragmentToQuizStatisticsFragment(course.courseName!!, quiz.name!!, 99, it))
        })

        viewModel.hashMap.observe(viewLifecycleOwner) {
            for (user in it.keys){
                val u = user
                val d = it[user]
            }
//            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}