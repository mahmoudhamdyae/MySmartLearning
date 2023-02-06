package com.mahmoudhamdyae.smartlearning.ui.course.quiz.answerquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizAnswerBinding

class AnswerQuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizAnswerBinding
    override val viewModel: AnswerQuizViewModel by viewModels {
        AnswerQuizViewModelFactory(FirebaseRepository())
    }

    private lateinit var quiz: Quiz
    private lateinit var courseId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizAnswerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quiz = AnswerQuizFragmentArgs.fromBundle(requireArguments()).quiz
        courseId = AnswerQuizFragmentArgs.fromBundle(requireArguments()).courseId

        viewModel.setValueOfQuiz(quiz)

        binding.option1.setOnClickListener {
            onAnswer(1)
        }
        binding.option2.setOnClickListener {
            onAnswer(2)
        }
        binding.option3.setOnClickListener {
            onAnswer(3)
        }
        binding.option4.setOnClickListener {
            onAnswer(4)
        }

        binding.nextQuestionButton.setOnClickListener {
            nextQuestion()
        }

        viewModel.navigateUp.observe(viewLifecycleOwner) {
            if (it) {
                // todo Show Degree
                viewModel.finishNavigating()
            }
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    confirmDiscard()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun onAnswer(answer: Int) {
    }

    private fun nextQuestion() {
    }

    private fun confirmDiscard() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.discard_message_in_answer)
            .setPositiveButton(R.string.discard_yes_in_answer) { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton(R.string.discard_no_in_answer) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}