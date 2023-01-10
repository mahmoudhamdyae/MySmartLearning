package com.mahmoudhamdyae.smartlearning.ui.courses

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMainBinding
import com.mahmoudhamdyae.smartlearning.utils.Constants


@Suppress("DEPRECATION")
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var mAUth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        // Initialize Firebase Auth
        mAUth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        observeAuthenticationState()

        return binding.root
    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     */
    private fun observeAuthenticationState() {
        if (mAUth.currentUser == null) {
            navigateToLoginScreen()
        } else {
            val mUsersReference =
                databaseReference.child(Constants.USERS).child(mAUth.currentUser!!.uid)
            val mCourseDatabaseReference = databaseReference.child(Constants.COURSES)

            mUsersReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user!!.isTeacher) {
                        Toast.makeText(context, "Welcome back " + user.userName, Toast.LENGTH_SHORT).show()
//                        teacherActivity(user)
                    } else {
                        Toast.makeText(context, "Welcome back " + user.userName, Toast.LENGTH_SHORT).show()
//                        studentActivity()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("MainFragment", "loadPost:onCancelled", databaseError.toException())
                }
            })
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
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                navigateToLoginScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}