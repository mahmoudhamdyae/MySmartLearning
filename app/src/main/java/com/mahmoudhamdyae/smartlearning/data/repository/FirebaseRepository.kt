package com.mahmoudhamdyae.smartlearning.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.utils.Constants
import java.io.File

class FirebaseRepository {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(Constants.USERS)
    private var courseDatabaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(Constants.COURSES)
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference

    fun getUid() = mAuth.currentUser!!.uid

    // Auth

    fun signUp(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun logIn(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    fun saveUserInDatabase(user: User): Task<Void> {
        return userDatabaseReference.child(getUid()).setValue(user)
    }

    fun saveProfilePicture(imageUri: Uri): UploadTask {
        return mStorageRef.child(Constants.IMAGES).child(getUid() + ".jpg").putFile(imageUri)
    }

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

    // Users

    fun addStudentToCourse(user: User, courseId: String): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.STUDENTS).child(user.userId!!).setValue(user)
    }

    fun getStudentsOfCourse(courseId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.STUDENTS)
    }

    fun getAllUsers(): DatabaseReference = userDatabaseReference

    fun updateNoOfStudents(courseId: String, noOfStudents: Int, teacher: User) {
        val updates: MutableMap<String, Any> = HashMap()
        updates["studentsNo"] = noOfStudents
        courseDatabaseReference.child(courseId).updateChildren(updates).addOnSuccessListener {
            userDatabaseReference.child(teacher.userId!!).child(Constants.COURSES).child(courseId).updateChildren(updates)
            getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (student in snapshot.children) {
                        val studentItem = student.getValue(User::class.java)
                        userDatabaseReference.child(studentItem!!.userId!!).child(Constants.COURSES).child(courseId).updateChildren(updates)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
                }
            })
        }
    }

    // Courses

    fun addCourseToCourses(course: Course) : Task<Void> {
        return courseDatabaseReference.child(course.id).setValue(course)
    }

    fun addCourseToUser(userId: String, course: Course): Task<Void> {
        return userDatabaseReference.child(userId).child(Constants.COURSES).child(course.id).setValue(course)
    }

    fun getUserCourses(): DatabaseReference {
        return userDatabaseReference.child(getUid()).child(Constants.COURSES)
    }

    fun delCourseFromCourses(courseId: String): Task<Void> {
        return courseDatabaseReference.child(courseId).removeValue()
    }

    fun delCourseFromStudents(courseId: String) {
        getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (student in snapshot.children) {
                    val studentItem = student.getValue(User::class.java)
                    userDatabaseReference.child(studentItem!!.userId!!).child(Constants.COURSES).child(courseId).removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
            }
        })
    }

    fun delCourseFromUser(courseId: String): Task<Void> {
        return userDatabaseReference.child(getUid()).child(Constants.COURSES).child(courseId).removeValue()
    }

    fun getAllCourses(): DatabaseReference = courseDatabaseReference

    // Materials

    fun addMaterialStorage(file: Uri, name: String, courseId: String): UploadTask {
        return mStorageRef.child(Constants.MATERIALS).child(courseId).child(name).putFile(file)
    }

    fun addMaterialsToDataBase(name: String, courseId: String): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.MATERIALS).push().setValue(name)
    }

    fun getMaterials(courseId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.MATERIALS)
    }

    fun getMaterial(courseId: String, name: String, localFile: File): FileDownloadTask {
        return mStorageRef.child(Constants.MATERIALS).child(courseId).child(name).getFile(localFile)
    }

    fun delMaterialStorage(courseId: String, name: String): Task<Void> {
        return mStorageRef.child(Constants.MATERIALS).child(courseId).child(name).delete()
    }

    fun delMaterialDatabase(courseId: String, key: String): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.MATERIALS).child(key).removeValue()
    }

    fun delMaterialsStorage(courseId: String): Task<Void> {
        return mStorageRef.child(Constants.MATERIALS).child(courseId).delete()
    }

    // Quizzes

    // Chats

    fun sendMessageGroup(courseId: String, message: Message): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.CHATS).child(message.time).setValue(message)
    }

    fun sendMessagePrivate(user: User, anotherUser: User, message: Message): Task<Void> {
        return userDatabaseReference.child(user.userId!!).child(Constants.CHATS).child(anotherUser.userId!!).child(message.time).setValue(message)
    }

    fun getGroupChat(courseId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.CHATS)
    }

    fun getPrivateChat(user: User, anotherUser: User): DatabaseReference {
        return userDatabaseReference.child(user.userId!!).child(Constants.CHATS).child(anotherUser.userId!!)
    }
}