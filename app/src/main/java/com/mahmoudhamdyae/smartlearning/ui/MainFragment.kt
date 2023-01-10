package com.mahmoudhamdyae.smartlearning.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMainBinding

@Suppress("DEPRECATION")
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        observeAuthenticationState()

        return binding.root
    }

    /**
     * Observes the authentication state and changes the UI accordingly.
     */
    private fun observeAuthenticationState() {
        if (auth.currentUser == null) {
            navigateToLoginScreen()
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