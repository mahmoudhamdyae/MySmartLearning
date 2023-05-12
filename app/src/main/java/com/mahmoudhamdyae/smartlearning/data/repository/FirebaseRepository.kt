package com.mahmoudhamdyae.smartlearning.data.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FileDownloadTask
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.Material
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.data.models.Question
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.models.User
import java.io.File

interface FirebaseRepository {

    fun getUid(): String

    // Auth
    fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun logIn(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun saveUserInDatabase(user: User, onResult: (Throwable?) -> Unit)
    suspend fun saveProfilePicture(imageUri: Uri, onResult: (Throwable?) -> Unit): Uri?
    fun getUserData(): MutableLiveData<User?>

    // Users
    fun addStudentToCourse(user: User, courseId: String, onResult: (Throwable?) -> Unit)
    fun getStudentsOfCourse(courseId: String, students: (List<User>) -> Unit, onResult: (DatabaseError?) -> Unit)
    fun getAllUsers(users: (List<User>) -> Unit, onResult: (DatabaseError?) -> Unit)
    fun updateNoOfStudents(courseId: String, noOfStudents: Int, teacher: User)
    fun getUserById(userId: String): DatabaseReference

    // Courses
    fun addCourseToCourses(course: Course, onResult: (Throwable?) -> Unit)
    fun addCourseToUser(userId: String, course: Course, onResult: (Throwable?) -> Unit)
    fun getUserCourses(courses: (List<Course>) -> Unit, onResult: (DatabaseError?) -> Unit)
    fun delCourseFromCourses(courseId: String, onResult: (Throwable?) -> Unit)
    fun delStudentFromCourse(uid: String, courseId: String, onResult: (Throwable?) -> Unit)
    fun delCourseFromStudents(courseId: String, onResult: (Throwable?) -> Unit)
    fun delCourseFromUser(courseId: String, onResult: (Throwable?) -> Unit)
    fun getAllCourses(courses: (List<Course>) -> Unit, onResult: (DatabaseError?) -> Unit)

    // Materials
    fun addMaterialStorage(file: Uri, name: String, courseId: String, onResult: (Throwable?) -> Unit)
    fun addMaterialsToDataBase(material: Material, courseId: String, onResult: (Throwable?) -> Unit)
    fun getMaterials(courseId: String, materials: (List<Material>) -> Unit, onResult: (DatabaseError?) -> Unit)
    fun getMaterial(courseId: String, name: String, localFile: File): FileDownloadTask
    fun delMaterialStorage(courseId: String, name: String, onResult: (Throwable?) -> Unit)
    fun delMaterialDatabase(courseId: String, material: Material, onResult: (Throwable?) -> Unit)
    fun delMaterialsStorage(courseId: String, onResult: (Throwable?) -> Unit)

    // Quizzes
    fun saveQuiz(courseId: String, quiz: Quiz, onResult: (Throwable?) -> Unit)
    fun addQuestions(courseId: String, quizId: String, quiz: MutableList<Question>, onResult: (Throwable?) -> Unit)
    fun updateQuestion(courseId: String, quizId: String, questionNo: Int, question: Question, onResult: (Throwable?) -> Unit)
    fun getQuizzes(courseId: String, hashMap: (HashMap<String, Int?>) -> Unit, quizzes: (List<Quiz>) -> Unit, onResult: (DatabaseError?) -> Unit)
    fun getDegree(courseId: String, quizId: String, userId: String, degree: (Double) -> Unit,onResult: (DatabaseError?) -> Unit)
    fun delQuiz(courseId: String, quizId: String, onResult: (Throwable?) -> Unit)
    fun getStudentsInQuiz(
        courseId: String,
        quizId: String,
        hashMap: (HashMap<User, Double>) -> Unit,
        students: (List<User>) -> Unit,
        onResult: (DatabaseError?) -> Unit
    )
    fun saveDegree(courseId: String, quizId: String, degree: Int, noOfQuestions: Int, onResult: (Throwable?) -> Unit)

    // Chats
    fun sendMessageGroup(courseId: String, message: Message, onResult: (Throwable?) -> Unit)
    fun sendMessagePrivate(user: User, anotherUser: User, message: Message, onResult: (Throwable?) -> Unit)
    fun getGroupChat(courseId: String, messages: (List<Message>) -> Unit, onResult: (DatabaseError?) -> Unit)
    fun getPrivateChat(user: User, anotherUser: User, messages: (List<Message>) -> Unit, onResult: (DatabaseError?) -> Unit)
}