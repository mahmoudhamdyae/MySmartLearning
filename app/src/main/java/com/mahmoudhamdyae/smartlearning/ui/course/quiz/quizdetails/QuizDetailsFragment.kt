package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizDetailsBinding

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
}