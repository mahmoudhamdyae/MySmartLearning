package com.mahmoudhamdyae.smartlearning.ui.courses

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMainBinding
import com.mahmoudhamdyae.smartlearning.ui.auth.LogInViewModel
import com.mahmoudhamdyae.smartlearning.utils.Constants

@Suppress("DEPRECATION")
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: CoursesViewModel
    private lateinit var logInViewModel: LogInViewModel

    private lateinit var mAUth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[CoursesViewModel::class.java]
        logInViewModel = ViewModelProvider(this)[LogInViewModel::class.java]

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.coursesList.adapter = CoursesAdapter(CoursesAdapter.OnClickListener {
            // Navigate to Course Fragment
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCourseFragment())
        })

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
        }
    }

    private fun saveUserLocally() {
        viewModel.user.observe(viewLifecycleOwner) {
            val userName = it.userName
            val isTeacher = it.teacher
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with(sharedPref!!.edit()) {
                putString(Constants.USERNAME, userName)
                putBoolean(Constants.ISTEACHER, isTeacher)
                apply()
            }
            getUserType()
        }
    }

    private fun getUserType() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val isTeacher = sharedPref.getBoolean(Constants.ISTEACHER, false)
        if (isTeacher) {
            teacherActivity()
        } else {
            studentActivity()
        }
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLogInFragment())
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onCreateOptionsMenu(menu, inflater)",
        "androidx.fragment.app.Fragment"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onOptionsItemSelected(item)",
        "androidx.fragment.app.Fragment"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.profile -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToProfileFragment())
                true
            }
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                navigateToLoginScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun teacherActivity() {

        binding.addFab.setOnClickListener {
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
    }

    private fun studentActivity() {
        binding.addFab.setOnClickListener {
            // Navigate to search fragment
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
        }
    }
}