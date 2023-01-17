package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizBinding
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher

class QuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizBinding
    override val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserType()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = QuizAdapter(QuizAdapter.OnClickListener {
        })
        viewModel.isTeacher.observe(viewLifecycleOwner) {
            adapter.setIsTeacher(it == IsTeacher.TEACHER)
        }

        binding.quizzesList.layoutManager = GridLayoutManager(context, 1)
        binding.quizzesList.adapter = adapter
    }
}