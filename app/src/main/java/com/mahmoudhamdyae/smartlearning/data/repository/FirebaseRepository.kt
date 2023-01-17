package com.mahmoudhamdyae.smartlearning.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.utils.Constants

class FirebaseRepository {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(Constants.USERS)
    private var courseDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(Constants.COURSES)
    private var materialsDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(Constants.MATERIALS)
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference

    fun getUid() = mAuth.currentUser!!.uid

    // Users

    fun signUp(email: String, password: String): Task<AuthResult> =
        mAuth.createUserWithEmailAndPassword(email, password)

    fun logIn(email: String, password: String): Task<AuthResult> =
        mAuth.signInWithEmailAndPassword(email, password)

    fun saveUserInDatabase(user: User): Task<Void> =
        userDatabaseReference.child(getUid()).setValue(user)

    fun saveProfilePicture(imageUri: Uri): UploadTask =
        mStorageRef.child(Constants.IMAGES).child(getUid() + ".jpg").putFile(imageUri)

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

    fun addStudentToCourse(user: User) {
    }

    fun getStudents() {
    }

    // Courses

    fun addCourse(course: Course) : Task<Void> =
        courseDatabaseReference.child(course.id).setValue(course).addOnSuccessListener {
            userDatabaseReference.child(getUid()).child(Constants.COURSES).child(course.id).setValue(course)
    }

    fun getCourses(): DatabaseReference =
        userDatabaseReference.child(getUid()).child(Constants.COURSES)

    fun delCourse(courseId: String): Task<Void> =
        courseDatabaseReference.child(courseId).removeValue().addOnSuccessListener {
            userDatabaseReference.child(getUid()).child(Constants.COURSES).child(courseId).removeValue()
        }

    // Materials

    fun addMaterialStorage(file: Uri, name: String, courseId: String): UploadTask =
        mStorageRef.child(Constants.MATERIALS).child(courseId).child(name).putFile(file)

    fun addMaterialsToDataBase(name: String, courseId: String): Task<Void> =
        materialsDatabaseReference.child(courseId).push().setValue(name)

    fun getMaterials(courseId: String): DatabaseReference =
        materialsDatabaseReference.child(courseId)

    // Quizzes

    // Chats
}