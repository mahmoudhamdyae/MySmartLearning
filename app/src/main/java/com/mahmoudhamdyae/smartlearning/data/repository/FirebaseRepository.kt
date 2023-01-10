package com.mahmoudhamdyae.smartlearning.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mahmoudhamdyae.smartlearning.data.models.User

class FirebaseRepository {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference.child("images")

    fun signUp(user: User, password: String) {
        mAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign up success.
                    Log.d("SignUp", "createUserWithEmail:success")
                    saveUserInDatabase(user)
                    saveProfilePicture(user.imageUri?.toUri())
                    _error.value = "SUCCESS"
                } else {
                    // Sign up fails
                    Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                    _error.value = task.exception.toString()
                }
            }
    }

    fun logIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Log in success.
                    Log.d("LogIn", "signInWithEmail:success")
                    _error.value = "SUCCESS"
                } else {
                    // Log in fails.
                    Log.w("LogIn", "signInWithEmail:failure", task.exception)
                    _error.value = task.exception.toString()
                }
            }
    }

    private fun saveUserInDatabase(user: User) {
        databaseReference.child(mAuth.currentUser!!.uid).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _error.value = "SUCCESS"
                } else {
                    _error.value = task.exception.toString()
                }
            }
    }

    private fun saveProfilePicture(imageUri: Uri?) {
        if (imageUri != null) {
            mStorageRef.child(mAuth.currentUser!!.uid + ".jpg").putFile(imageUri)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _error.value = "SUCCESS"
                    } else {
                        _error.value = task.exception.toString()
                    }
                }
        }
    }
}