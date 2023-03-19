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
import com.mahmoudhamdyae.smartlearning.data.models.*
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

    fun getProfilePicture(uid: String): Task<Uri> {
        return mStorageRef.child(Constants.IMAGES).child("$uid.jpg").downloadUrl
    }

    // Users

    fun addStudentToCourse(user: User, courseId: String): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.STUDENTS).child(user.id).setValue(user)
    }

    fun getStudentsOfCourse(courseId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.STUDENTS)
    }

    fun getAllUsers(): DatabaseReference = userDatabaseReference

    fun updateNoOfStudents(courseId: String, noOfStudents: Int, teacher: User) {
        val updates: MutableMap<String, Any> = HashMap()
        updates["studentsNo"] = noOfStudents
        courseDatabaseReference.child(courseId).updateChildren(updates).addOnSuccessListener {
            userDatabaseReference.child(teacher.id).child(Constants.COURSES).child(courseId).updateChildren(updates)
            getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (student in snapshot.children) {
                        val studentItem = student.getValue(User::class.java)
                        userDatabaseReference.child(studentItem!!.id).child(Constants.COURSES).child(courseId).updateChildren(updates)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
                }
            })
        }
    }

    fun getUserById(userId: String): DatabaseReference {
        return userDatabaseReference.child(userId)
    }

    // Courses

    fun addCourseToCourses(course: Course, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(course.id).setValue(course).addOnCompleteListener{
            onResult(it.exception)
        }
    }

    fun addCourseToUser(userId: String, course: Course, onResult: (Throwable?) -> Unit) {
        userDatabaseReference.child(userId).child(Constants.COURSES).child(course.id)
            .setValue(course).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    fun getUserCourses(courses: (List<Course>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        userDatabaseReference.child(getUid()).child(Constants.COURSES)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val coursesList : MutableList<Course> = mutableListOf()
                for (course in dataSnapshot.children) {
                    val courseItem = course.getValue(Course::class.java)
                    coursesList.add(courseItem!!)
                }
                courses(coursesList)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("getCourses:onCancelled", "loadCourses:onCancelled", databaseError.toException())
                onResult(databaseError)
            }

        })
    }

    fun delCourseFromCourses(courseId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).removeValue().addOnCompleteListener {
            onResult(it.exception)
        }
    }
    fun delStudentFromCourse(uid: String, courseId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.STUDENTS).child(uid).removeValue()
            .addOnCompleteListener {
            onResult(it.exception)
        }
    }

    fun delCourseFromStudents(courseId: String, onResult: (Throwable?) -> Unit) {
        getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (student in snapshot.children) {
                    val studentItem = student.getValue(User::class.java)
                    userDatabaseReference.child(studentItem!!.id).child(Constants.COURSES)
                        .child(courseId).removeValue().addOnCompleteListener {
                            onResult(it.exception)
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
            }
        })
    }

    fun delCourseFromUser(courseId: String, onResult: (Throwable?) -> Unit) {
        userDatabaseReference.child(getUid()).child(Constants.COURSES).child(courseId)
            .removeValue().addOnCompleteListener {
                onResult(it.exception)
            }
    }

    fun getAllCourses(courses: (List<Course>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        courseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val coursesList: MutableList<Course> = mutableListOf()
                for (course in snapshot.children) {
                    val courseItem = course.getValue(Course::class.java)
                    getUserCourses({courses ->
                        var isEnteredCourse = false
                        for (userCourse in courses) {
                            if (courseItem!!.id == userCourse.id) {
                                isEnteredCourse = true
                                break
                            }
                        }
                        if (!isEnteredCourse) {
                            coursesList.add(courseItem!!)
                        }
                        courses(coursesList)
                    }, { error ->
                        if (error != null) {
                            onResult(error)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(
                    "getStudents:Cancelled",
                    "loadStudents:onCancelled",
                    error.toException()
                )
                onResult(error)
            }
        })
    }

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

    fun saveQuiz(courseId: String, quiz: Quiz): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quiz.id).setValue(quiz)
    }

    fun addQuestions(courseId: String, quizId: String, quiz: MutableList<Question>): Task<Void> {
        val childUpdates = hashMapOf<String, Any>(
            "/$quizId/${Constants.QUESTIONS}" to quiz
        )
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).updateChildren(childUpdates)
    }

    fun updateQuestion(courseId: String, quizId: String, questionNo: Int, question: Question): Task<Void> {
        val questionValues = question.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${Constants.QUESTIONS}/${questionNo}" to questionValues
        )
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId)
            .updateChildren(childUpdates)
    }

    fun getQuizzes(courseId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes)
    }

    fun getDegree(courseId: String, quizId: String, userId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId).child(Constants.STUDENTS).child(userId)
    }

    fun delQuiz(courseId: String, quizId: String): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId).removeValue()
    }

    fun getStudentsInQuiz(courseId: String, quizId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId).child(Constants.STUDENTS)
    }

    fun saveDegree(courseId: String, quizId: String, degree: Int, noOfQuestions: Int): Task<Void> {
        val percentDegree: Double = 100.0 * degree / noOfQuestions
        return courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId).child(Constants.STUDENTS).child(getUid()).setValue(percentDegree)
    }

    // Chats

    fun sendMessageGroup(courseId: String, message: Message): Task<Void> {
        return courseDatabaseReference.child(courseId).child(Constants.CHATS).child(message.time).setValue(message)
    }

    fun sendMessagePrivate(user: User, anotherUser: User, message: Message): Task<Void> {
        return userDatabaseReference.child(user.id).child(Constants.CHATS).child(anotherUser.id).child(message.time).setValue(message)
    }

    fun getGroupChat(courseId: String): DatabaseReference {
        return courseDatabaseReference.child(courseId).child(Constants.CHATS)
    }

    fun getPrivateChat(user: User, anotherUser: User): DatabaseReference {
        return userDatabaseReference.child(user.id).child(Constants.CHATS).child(anotherUser.id)
    }
}