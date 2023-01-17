package com.mahmoudhamdyae.smartlearning.ui.courses

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.models.Course
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
        binding.coursesList.adapter = CoursesAdapter(CoursesAdapter.OnClickListener {
            // Navigate to Course Fragment
            findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToCourseFragment(it.id))
        }, false)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profile -> {
                    findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToProfileFragment())
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

    /**
     * Observes the authentication state and changes the UI accordingly.
     */
    private fun observeAuthenticationState() {
        if (mAUth.currentUser == null) {
            navigateToLoginScreen()
        } else {
            saveUserLocally()
            viewModel.getListOfCourses()
        }
    }

    private fun saveUserLocally() {
        // Get user from Firebase and save it in user LiveData object
        viewModel.getUserData()
        // Observe User Livedata
        viewModel.user.observe(viewLifecycleOwner) {
            if (it!!.teacher) {
                teacherActivity()
            } else {
                studentActivity()
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
//
            val course = Course("test", "testYear", "myName", 10)
            viewModel.addCourse(course)
        }
    }

    private fun createDialog() {
//            // Create AlertDialog and show it.
//            val layoutInflater = LayoutInflater.from(context)
//            val popupInputDialogView = layoutInflater.inflate(R.layout.course_dialog, null)
//            // Create a AlertDialog Builder.
//            val alertDialogBuilder: AlertDialog.Builder? = activity?.let {
//                AlertDialog.Builder(it)
//            }
//            // Set title, icon, can not cancel properties.
//            alertDialogBuilder?.setCancelable(true)
//            // Init popup dialog view and it's ui controls.
//
//            // Set the inflated layout view object to the AlertDialog builder.
//            alertDialogBuilder?.setView(popupInputDialogView)
//            val alertDialog: AlertDialog = alertDialogBuilder!!.create()
//            alertDialog.show()
//            alertDialogBuilder.setView(popupInputDialogView)
//
//            val dialogBinding = CourseDialogBinding.inflate(layoutInflater)
//            dialogBinding.viewModel = viewModel
//            dialogBinding.saveButton.setOnClickListener {
//                viewModel.addCourse()
//            }
//            dialogBinding.cancelButton.setOnClickListener {
//                alertDialog.dismiss()
//            }

//            Toast.makeText(context, "haha Teacher", Toast.LENGTH_SHORT).show()
//            val dialog = Dialog(requireContext())
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setCancelable(false)
//            dialog.setContentView(R.layout.course_dialog)
//            dialog.create()

//            val body = dialog.findViewById(R.id.body) as TextView
//            val dialogBinding = CourseDialogBinding.inflate(layoutInflater)
//            dialogBinding.viewModel = viewModel
//            body.text = title
//            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//            val noBtn = dialog.findViewById(R.id.noBtn) as Button
//            dialogBinding.saveButton.setOnClickListener {
//                dialog.dismiss()
//            }
//            dialogBinding.cancelButton.setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.show()
    }

    private fun studentActivity() {
        binding.addFab.setOnClickListener {
            // Navigate to search fragment
            findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToSearchFragment())
        }
    }
}