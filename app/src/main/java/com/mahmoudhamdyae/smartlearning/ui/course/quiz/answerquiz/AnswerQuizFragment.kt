package com.mahmoudhamdyae.smartlearning.ui.course.quiz.answerquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizAnswerBinding

class AnswerQuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizAnswerBinding
    override val viewModel: AnswerQuizViewModel by viewModels()

    private lateinit var quiz: Quiz
    private lateinit var course: Course
    private lateinit var user: User

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
        course = AnswerQuizFragmentArgs.fromBundle(requireArguments()).course
        user = AnswerQuizFragmentArgs.fromBundle(requireArguments()).user

        viewModel.setValueOfQuiz(quiz, course.id)

        binding.option1.setOnClickListener {
            onAnswer(1, it)
        }
        binding.option2.setOnClickListener {
            onAnswer(2, it)
        }
        binding.option3.setOnClickListener {
            onAnswer(3, it)
        }
        binding.option4.setOnClickListener {
            onAnswer(4, it)
        }

        binding.nextQuestionButton.setOnClickListener {
            nextQuestion()
            it.visibility = View.GONE
        }

        viewModel.navigateUp.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.degree.observe(viewLifecycleOwner) { degree ->
                    val percentDegree = degree * 100 / quiz.questions.size
                    findNavController().navigate(AnswerQuizFragmentDirections.actionAnswerQuizFragmentToQuizStatisticsFragment(course.courseName!!, quiz.name!!, percentDegree, user))
                }
                viewModel.finishNavigating()
            }
        }

        viewModel.num.observe(viewLifecycleOwner) {
            if (quiz.questions.size == it) {
                binding.nextQuestionButton.text = getString(R.string.add_quiz_finish_button)
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

    private fun onAnswer(answer: Int, view: View) {
        binding.option1.isClickable = false
        binding.option2.isClickable = false
        binding.option3.isClickable = false
        binding.option4.isClickable = false

        if (viewModel.onAnswer(answer)) {
            view.background = ContextCompat.getDrawable(requireContext(), R.color.green)
        } else {
            view.background = ContextCompat.getDrawable(requireContext(), R.color.red)
            viewModel.answer.observe(viewLifecycleOwner) {
                when (it) {
                    1 -> {
                        binding.option1.background = ContextCompat.getDrawable(requireContext(), R.color.green)
                    }
                    2 -> {
                        binding.option2.background = ContextCompat.getDrawable(requireContext(), R.color.green)
                    }
                    3 -> {
                        binding.option3.background = ContextCompat.getDrawable(requireContext(), R.color.green)
                    }
                    4 -> {
                        binding.option4.background = ContextCompat.getDrawable(requireContext(), R.color.green)
                    }
                }
            }
        }

        binding.nextQuestionButton.visibility = View.VISIBLE
    }

    private fun nextQuestion() {
        viewModel.setNextQuestion()

        binding.option1.background = ContextCompat.getDrawable(requireContext(), R.color.grey)
        binding.option2.background = ContextCompat.getDrawable(requireContext(), R.color.grey)
        binding.option3.background = ContextCompat.getDrawable(requireContext(), R.color.grey)
        binding.option4.background = ContextCompat.getDrawable(requireContext(), R.color.grey)

        binding.option1.isClickable = true
        binding.option2.isClickable = true
        binding.option3.isClickable = true
        binding.option4.isClickable = true
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