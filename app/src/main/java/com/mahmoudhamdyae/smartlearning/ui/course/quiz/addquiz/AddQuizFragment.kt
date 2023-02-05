package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
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
        viewModel.getQuiz(quiz)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.option1_radio_button) {
            } else if (checkedId == R.id.option2_radio_button) {
            } else if (checkedId == R.id.option3_radio_button) {
            } else { // option 4
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
        viewModel.finish()
        findNavController().navigateUp()
    }
}