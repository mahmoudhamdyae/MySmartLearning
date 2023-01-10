package com.mahmoudhamdyae.smartlearning.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mahmoudhamdyae.smartlearning.databinding.FragmentLogInBinding
import com.mahmoudhamdyae.smartlearning.ui.auth.LogInViewModel

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewModel: LogInViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LogInViewModel::class.java]
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.logInButton.setOnClickListener {
            logIn()
        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun logIn() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (viewModel.validateTextsLogIn(email, password)) {

            viewModel.logIn(email, password)

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("LogIn", "signInWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainFragment())
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LogIn", "signInWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}