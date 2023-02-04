package com.mahmoudhamdyae.smartlearning.ui.courses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentCoursesBinding
import com.mahmoudhamdyae.smartlearning.utils.Constants

class CoursesFragment: BaseFragment() {

    private lateinit var binding: FragmentCoursesBinding
    override val viewModel: CoursesViewModel by viewModels {
        CoursesViewModelFactory(FirebaseRepository())
    }

    private lateinit var mAUth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private var isTeacher = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.toolbar.inflateMenu(R.menu.menu_main)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coursesList.layoutManager = GridLayoutManager(context, 1)
        val adapter = CoursesAdapter(CoursesAdapter.OnClickListener { course ->
            viewModel.user.observe(viewLifecycleOwner) { user ->
                findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToCourseFragment(course, user!!))
            }
        },  CoursesAdapter.OnDelClickListener { course ->
            delCourse(course)
        })
        binding.coursesList.adapter = adapter

        viewModel.user.observe(viewLifecycleOwner) { user ->
            adapter.setVisibility(user!!.teacher, false)
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.notification_item -> {
                    findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToNotificationFragment())
                    true
                }
                R.id.profile -> {
                    viewModel.user.observe(viewLifecycleOwner) { user ->
                        findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToProfileFragment(user!!))
                    }
                    true
                }
                R.id.sign_out -> {
                    FirebaseAuth.getInstance().signOut()
                    navigateToLoginScreen()
                    true
                }
                else -> false
            }
        }

        // Initialize Firebase Auth
        mAUth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        observeAuthenticationState()
    }

    private fun delCourse(course: Course) {
        // Create Dialog
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setMessage(getString(R.string.course_delete_dialog_msg))
        builder.setPositiveButton(R.string.course_delete_dialog_delete) { _, _ ->
            // User clicked the "Delete" button, so delete the Course.
            if (isTeacher) {
                viewModel.delCourse(course.id)
            } else {
                viewModel.decreaseNoOfStudents(course)
                viewModel.delStudentFromCourse(course.id)
            }
            viewModel.delCourseFromUser(course.id)
        }
        builder.setNegativeButton(R.string.course_delete_dialog_cancel) { dialog, _ ->
                // User clicked the "Cancel" button, so dismiss the dialog
                dialog?.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     */
    private fun observeAuthenticationState() {
        if (mAUth.currentUser == null) {
            navigateToLoginScreen()
        } else {
            viewModel.getUserData()
            saveUserLocally()
            viewModel.getListOfCourses()
        }
    }

    private fun saveUserLocally() {
        // Observe User Livedata
        viewModel.user.observe(viewLifecycleOwner) {
            isTeacher = if (it!!.teacher) {
                teacherActivity()
                true
            } else {
                studentActivity()
                false
            }

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with(sharedPref!!.edit()) {
                putString(Constants.USERNAME, it.userName)
                putBoolean(Constants.ISTEACHER, it.teacher)
                apply()
            }
        }
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToLogInFragment())
    }

    private fun teacherActivity() {
        binding.addFab.setOnClickListener {
            createDialog()
        }
    }

    private fun createDialog() {
        val customAlertDialogView = LayoutInflater.from(context)
            .inflate(R.layout.course_dialog, null, false)
        val courseNameEditText = customAlertDialogView.findViewById<EditText>(R.id.course_name_edit_text)
        // Year Spinner
        val yearList: MutableList<String> = ArrayList()
        yearList.add("1")
        yearList.add("2")
        yearList.add("3")
        val yearAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            yearList
        )
        val yearSpinner = customAlertDialogView.findViewById<Spinner>(R.id.year)
        yearSpinner.adapter = yearAdapter

        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle(R.string.course_dialog_add_new_course)
                // Save Button
            .setPositiveButton(R.string.course_dialog_save_button) { dialog, _ ->
                val courseName  = courseNameEditText.text.toString()
                val courseYear = yearSpinner.selectedItem.toString()
                var teacher = User()
                viewModel.user.observe(viewLifecycleOwner) {
                    teacher = it!!
                }
                val course = Course(courseName = courseName, year = courseYear, teacher = teacher)
                viewModel.addCourse(course)
                dialog.dismiss()
            }
                // Cancel Button
            .setNegativeButton(R.string.course_dialog_cancel_button) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun studentActivity() {
        binding.addFab.setOnClickListener {
            // Navigate to search fragment
            viewModel.user.observe(viewLifecycleOwner) {
                findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToSearchFragment(it!!))
            }
        }
    }
}