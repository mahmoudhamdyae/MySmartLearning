package com.mahmoudhamdyae.smartlearning.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mahmoudhamdyae.smartlearning.data.models.User

class LogInViewModel : ViewModel() {

    // EditTexts fields
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean>
        get() = _navigate

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")

    private fun validateTextsSignUp(): Boolean {
        return if (userName.value.isNullOrEmpty() || email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            _error.value = "Field Can\'t be empty"
            false
        } else {
            true
        }
    }

    private fun validateTextsLogIn(): Boolean {
        return if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            _error.value = "Field Can\'t be empty"
            false
        } else {
            true
        }
    }

    fun signUp() {
        if (validateTextsSignUp()) {

            _loading.value = true
            mAuth.createUserWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign up success.
                        Log.d("SignUp", "createUserWithEmail:success")
                        saveUserInDatabase()
                        navigate()
                    } else {
                        // Sign up fails
                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                    }
                    _loading.value = false
                }
        }
    }

    fun logIn() {
        if (validateTextsLogIn()) {

            _loading.value = true
            mAuth.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Log in success.
                        Log.d("LogIn", "signInWithEmail:success")
                        navigate()
                    } else {
                        // Log in fails.
                        Log.w("LogIn", "signInWithEmail:failure", task.exception)
                    }
                    _loading.value = false
                }
        }
    }

    private fun saveUserInDatabase() {
        val user = User(userName.value!!, email.value!!, true, mAuth.currentUser!!.uid)
        databaseReference.child(mAuth.currentUser!!.uid).setValue(user).addOnSuccessListener {
        }.addOnFailureListener {
                _error.value = it.message
            }
    }

    private fun navigate() {
        _navigate.value = true
    }

    fun finishNavigate() {
        _navigate.value = false
    }
}