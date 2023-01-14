package com.mahmoudhamdyae.smartlearning.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.utils.Constants

class FirebaseRepository {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(Constants.USERS)
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference.child(Constants.IMAGES)

    fun getUid() = mAuth.currentUser!!.uid

    fun signUp(email: String, password: String) =
        mAuth.createUserWithEmailAndPassword(email, password)

    fun logIn(email: String, password: String) =
        mAuth.signInWithEmailAndPassword(email, password)

    fun saveUserInDatabase(user: User) =
        userDatabaseReference.child(getUid()).setValue(user)

    fun saveProfilePicture(imageUri: Uri) =
        mStorageRef.child(getUid() + ".jpg").putFile(imageUri)

    fun getUserData(): MutableLiveData<User?> {
        val user = MutableLiveData<User?>()

        userDatabaseReference.child(getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user.value =  dataSnapshot.getValue(User::class.java)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("LogInViewModel", "loadPost:onCancelled", databaseError.toException())
            }
        })

        return user
    }
}