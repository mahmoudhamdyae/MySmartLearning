package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

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
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizAddBinding

class AddQuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizAddBinding
    override val viewModel: AddQuizViewModel by viewModels {
        AddQuizViewModelFactory(FirebaseRepository())
    }

    private var addType: Int = 0
    private lateinit var quiz: Quiz
    private lateinit var courseId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizAddBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addType = AddQuizFragmentArgs.fromBundle(requireArguments()).addType
        quiz = AddQuizFragmentArgs.fromBundle(requireArguments()).quiz
        courseId = AddQuizFragmentArgs.fromBundle(requireArguments()).courseId

        when (addType) {
            0 -> { // Add Quiz
                viewModel.setNumValue(1)
            }
            1 -> { // Modify Quiz
                viewModel.putValuesToEditTexts(quiz.questions[0])
                when (quiz.questions[0].answer) {
                    1 -> { binding.option1RadioButton.isChecked = true }
                    2 -> { binding.option2RadioButton.isChecked = true }
                    3 -> { binding.option3RadioButton.isChecked = true }
                    4 -> { binding.option4RadioButton.isChecked = true }
                }
                binding.addAnotherQuestionButton.setText(R.string.add_quiz_modify_question_button)
                viewModel.setNumValue(1)
            }
            else -> { // Add Question
                viewModel.setNumValue(quiz.questions.size.plus(1))
            }
        }

        viewModel.setValueOfQuiz(quiz)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.option1_radio_button -> viewModel.answer.value = 1
                R.id.option2_radio_button -> viewModel.answer.value = 2
                R.id.option3_radio_button -> viewModel.answer.value = 3
                R.id.option4_radio_button -> viewModel.answer.value = 4
            }
        }

        viewModel.answer.observe(viewLifecycleOwner) {
            when (it) {
                1 -> binding.option1RadioButton.isChecked = true
                2 -> binding.option2RadioButton.isChecked = true
                3 -> binding.option3RadioButton.isChecked = true
                4 -> binding.option4RadioButton.isChecked = true
                else -> {
                    binding.radioGroup.clearCheck()
                }
            }
        }

        binding.addAnotherQuestionButton.setOnClickListener {
            addAnotherQuestion()
        }

        binding.finishButton.setOnClickListener {
            finish()
        }

        viewModel.navigateUp.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
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

    private fun addAnotherQuestion() {
        if (addType == 1) { // Modify Questions
            viewModel.validateAndUpdateQuestion(courseId)
        } else {
            viewModel.validateAndAddQuestion()
        }
    }

    private fun finish() {
        if (addType == 1) {
            viewModel.finishUpdate(courseId)
        } else {
            viewModel.finishAdd(courseId)
        }
    }

    private fun confirmDiscard() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.discard_message)
            .setPositiveButton(R.string.discard_yes) { _, _ ->
                findNavController().navigateUp()
            }
            .setNegativeButton(R.string.discard_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}