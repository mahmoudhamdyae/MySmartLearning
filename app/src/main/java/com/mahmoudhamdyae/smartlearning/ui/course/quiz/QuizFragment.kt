package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizBinding

class QuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizBinding
    override lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserType()

        binding.quizzesList.layoutManager = GridLayoutManager(context, 1)
        binding.quizzesList.adapter = QuizAdapterForStudent(QuizAdapterForStudent.OnClickListener {
        })
    }
}