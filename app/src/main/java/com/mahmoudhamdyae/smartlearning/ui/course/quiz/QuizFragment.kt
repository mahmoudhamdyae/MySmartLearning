package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.databinding.FragmentQuizBinding

class QuizFragment: BaseFragment() {

    private lateinit var binding: FragmentQuizBinding
    override val viewModel: QuizViewModel by viewModels()

    private lateinit var course: Course
    private lateinit var user: User

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserType()

        user = QuizFragmentArgs.fromBundle(requireArguments()).user
        course = QuizFragmentArgs.fromBundle(requireArguments()).course!!
        viewModel.getListOfQuizzes(course.id)

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = QuizAdapter(QuizAdapter.OnClickListener { quiz ->
            if (user.teacher) {
                findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizDetailsFragment(quiz, course))
            } else {
                // if not solved
                findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToAnswerQuizFragment(quiz, course, user))
                // todo if solved
//                    findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizStatisticsFragment(course.courseName!!, quiz.name!!, 99, user))
            }
        }, QuizAdapter.OnDelClickListener {
            delQuiz(it.id)
        })

        viewModel.hashMap.observe(viewLifecycleOwner) {
            adapter.setHashMap(it)
            adapter.notifyDataSetChanged()
        }

        adapter.setIsTeacher(user.teacher)
        if (user.teacher) {
            binding.addFab.setOnClickListener {
                addQuiz()
            }
        }

        binding.quizzesList.layoutManager = GridLayoutManager(context, 1)
        binding.quizzesList.adapter = adapter
    }

    private fun addQuiz() {
        val editText = EditText(context)
        editText.setPadding(16, 0, 16, 0)
        editText.setHint(R.string.add_quiz_dialog_edit_text_hint)
        MaterialAlertDialogBuilder(requireContext())
            .setView(editText)
            .setIcon(R.drawable.quiz)
            .setTitle(R.string.add_quiz_dialog_title)
            .setPositiveButton(R.string.add_quiz_dialog_positive_button) { _, _ ->
                val quizName = editText.text.toString()
                if (quizName.isEmpty()) {
                    Toast.makeText(context, R.string.quiz_name_edit_text_empty, Toast.LENGTH_SHORT).show()
                } else {
                    val quiz = Quiz(quizName)
                    findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToAddQuizFragment(quiz, course.id, 0))
                }
            }
            .setNegativeButton(R.string.add_quiz_dialog_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun delQuiz(quizId: String) {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        materialAlertDialogBuilder
            .setMessage(R.string.quiz_delete_dialog_msg)
            // Delete Button
            .setPositiveButton(R.string.quiz_delete_dialog_delete) { dialog, _ ->
                viewModel.delQuiz(course.id, quizId)
                dialog.dismiss()
            }
            // Cancel Button
            .setNegativeButton(R.string.quiz_delete_dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}