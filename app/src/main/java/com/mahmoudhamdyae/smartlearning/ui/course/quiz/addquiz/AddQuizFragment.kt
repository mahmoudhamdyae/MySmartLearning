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
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizAddBinding
import com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails.QuizDetailsFragmentArgs

class AddQuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizAddBinding
    override val viewModel: AddQuizViewModel by viewModels {
        AddQuizViewModelFactory(FirebaseRepository())
    }

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

        val addType = AddQuizFragmentArgs.fromBundle(requireArguments()).addType
        val quiz = AddQuizFragmentArgs.fromBundle(requireArguments()).quiz
        courseId = AddQuizFragmentArgs.fromBundle(requireArguments()).courseId

        if (addType == 0) { // Add Quiz
            viewModel.num.value = 1
        } else if (addType == 1) { // Modify Quiz
        } else { // Add Question
            viewModel.num.value = quiz.questions?.size?.plus(1)
        }

        viewModel.setValueOfQuiz(quiz)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.answer.value = when (checkedId) {
                R.id.option1_radio_button -> 1
                R.id.option2_radio_button -> 2
                R.id.option3_radio_button -> 3
                else -> 4
            }
        }

        viewModel.questionAdded.observe(viewLifecycleOwner) {
            if (it) {
                binding.option1RadioButton.isChecked = false
                binding.option2RadioButton.isChecked = false
                binding.option3RadioButton.isChecked = false
                binding.option4RadioButton.isChecked = false
                viewModel.addedQuestion()
            }
        }

        binding.addAnotherQuestionButton.setOnClickListener {
            addAnotherQuestion()
        }

        binding.finishButton.setOnClickListener {
            finish()
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
        viewModel.validateAndAddQuestion()
    }

    private fun finish() {
        if (viewModel.finish(courseId)) {
            findNavController().navigateUp()
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