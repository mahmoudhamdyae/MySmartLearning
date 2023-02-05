package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizAddBinding

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

        val quiz = AddQuizFragmentArgs.fromBundle(requireArguments()).quiz
        courseId = AddQuizFragmentArgs.fromBundle(requireArguments()).courseId
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
    }

    private fun addAnotherQuestion() {
        viewModel.validateAndAddQuestion()
    }

    private fun finish() {
        if (viewModel.finish(courseId)) {
            findNavController().navigateUp()
        }
    }
}