package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizstatistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizStatisticsBinding

class QuizStatisticsFragment: Fragment() {

    private lateinit var binding: FragmentQuizStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizStatisticsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseName = QuizStatisticsFragmentArgs.fromBundle(requireArguments()).courseName
        val quizName = QuizStatisticsFragmentArgs.fromBundle(requireArguments()).quizName
        val degree = QuizStatisticsFragmentArgs.fromBundle(requireArguments()).degree
        val user = QuizStatisticsFragmentArgs.fromBundle(requireArguments()).user

        val text = "${courseName}\n${quizName}\n${degree}\n${user.userName}"
        binding.statisticsText.text = text

        binding.okButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}