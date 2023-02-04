package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoudhamdyae.smartlearning.R
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

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = QuizAdapter(QuizAdapter.OnClickListener {
        })
        viewModel.isTeacher.observe(viewLifecycleOwner) {
            adapter.setIsTeacher(it == IsTeacher.TEACHER)
            if (it == IsTeacher.TEACHER) {
                binding.addFab.setOnClickListener {
                    addQuiz()
                }
            }
        }

        binding.quizzesList.layoutManager = GridLayoutManager(context, 1)
        binding.quizzesList.adapter = adapter
    }

    private fun addQuiz() {
        MaterialAlertDialogBuilder(requireContext())
            .setIcon(R.drawable.quiz)
            .setTitle(R.string.add_quiz_dialog_title)
            .setMessage(R.string.add_quiz_dialog_edit_text_hint)
            .setPositiveButton(R.string.add_quiz_dialog_positive_button) { _, _ ->
                findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToAddQuizFragment())
            }
            .setNegativeButton(R.string.add_quiz_dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}