package com.mahmoudhamdyae.smartlearning.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.Material
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.data.models.Question
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.utils.Constants
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val mAuth: FirebaseAuth,
    mDatabase: FirebaseDatabase,
    private val mStorage: FirebaseStorage,
): FirebaseRepository {

    private var userDatabaseReference: DatabaseReference = mDatabase.reference.child(Constants.USERS)
    private var courseDatabaseReference: DatabaseReference = mDatabase.reference.child(Constants.COURSES)

    override fun getUid() = mAuth.currentUser!!.uid

    // Auth

    override fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun logIn(email: String, password: String, onResult: (Throwable?) -> Unit) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun saveUserInDatabase(user: User, onResult: (Throwable?) -> Unit) {
        userDatabaseReference.child(getUid()).setValue(user).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override suspend fun saveProfilePicture(imageUri: Uri, onResult: (Throwable?) -> Unit): Uri? {
        mStorage.reference.child(Constants.IMAGES).child(getUid() + ".jpg").putFile(imageUri).addOnCompleteListener {
            onResult(it.exception)
        }
        return mStorage.reference.child(Constants.IMAGES).child(getUid() + ".jpg").downloadUrl.await()
    }

    override fun getUserData(): MutableLiveData<User?> {
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

    override fun addStudentToCourse(user: User, courseId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.STUDENTS).child(user.id)
            .setValue(user).addOnCompleteListener {
                onResult(it.exception)
        }
    }

    override fun getStudentsOfCourse(courseId: String, students: (List<User>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.STUDENTS)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val studentsList: MutableList<User> = mutableListOf()
                for (student in dataSnapshot.children) {
                    val studentItem = student.getValue(User::class.java)
                    if (getUid() != studentItem!!.id) {
                        studentsList.add(studentItem)
                    }
                }
                students(studentsList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(error)
            }
        })
    }

    override fun getAllUsers(users: (List<User>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        userDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersList: MutableList<User> = mutableListOf()
                for (user in snapshot.children) {
                    val userItem = user.getValue(User::class.java)
                    usersList.add(userItem!!)
                }
                users(usersList)
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

    override fun updateNoOfStudents(courseId: String, noOfStudents: Int, teacher: User) {
        val updates: MutableMap<String, Any> = HashMap()
        updates["studentsNo"] = noOfStudents
        courseDatabaseReference.child(courseId).updateChildren(updates).addOnSuccessListener {
            userDatabaseReference.child(teacher.id).child(Constants.COURSES).child(courseId).updateChildren(updates)
            getStudentsOfCourse(courseId, { students ->
                for (studentItem in students) {
                    userDatabaseReference.child(studentItem.id).child(Constants.COURSES).child(courseId).updateChildren(updates)
                }
            }, { error ->
                if (error != null) {
                    Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
                }
            })
        }
    }

    override fun getUserById(userId: String): DatabaseReference {
        return userDatabaseReference.child(userId)
    }

    // Courses

    override fun addCourseToCourses(course: Course, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(course.id).setValue(course).addOnCompleteListener{
            onResult(it.exception)
        }
    }

    override fun addCourseToUser(userId: String, course: Course, onResult: (Throwable?) -> Unit) {
        userDatabaseReference.child(userId).child(Constants.COURSES).child(course.id)
            .setValue(course).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun getUserCourses(courses: (List<Course>) -> Unit, onResult: (DatabaseError?) -> Unit) {
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

    override fun delCourseFromCourses(courseId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).removeValue().addOnCompleteListener {
            onResult(it.exception)
        }
    }
    override fun delStudentFromCourse(uid: String, courseId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.STUDENTS).child(uid).removeValue()
            .addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun delCourseFromStudents(courseId: String, onResult: (Throwable?) -> Unit) {
        getStudentsOfCourse(courseId, { students ->
            for (studentItem in students) {
                userDatabaseReference.child(studentItem.id).child(Constants.COURSES)
                    .child(courseId).removeValue().addOnCompleteListener {
                        onResult(it.exception)
                    }
            }
        }, { error ->
            if (error != null) {
                Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
            }
        })
    }

    override fun delCourseFromUser(courseId: String, onResult: (Throwable?) -> Unit) {
        userDatabaseReference.child(getUid()).child(Constants.COURSES).child(courseId)
            .removeValue().addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun getAllCourses(courses: (List<Course>) -> Unit, onResult: (DatabaseError?) -> Unit) {
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

    override fun addMaterialStorage(file: Uri, name: String, courseId: String, onResult: (Throwable?) -> Unit) {
        mStorage.reference.child(Constants.MATERIALS).child(courseId).child(name).putFile(file)
            .addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun addMaterialsToDataBase(material: Material, courseId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.MATERIALS).child(material.id)
            .setValue(material).addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun getMaterials(courseId: String, materials: (List<Material>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.MATERIALS)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val materialsList: MutableList<Material> = mutableListOf()
                for (material in dataSnapshot.children) {
                    val materialItem = material.getValue(Material::class.java)
                    materialsList.add(materialItem!!)
                }
                materials(materialsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("getMaterials:Cancelled", "loadMaterials:onCancelled", error.toException())
                onResult(error)
            }
        })
    }

    override fun getMaterial(courseId: String, name: String, localFile: File): FileDownloadTask {
        return mStorage.reference.child(Constants.MATERIALS).child(courseId).child(name).getFile(localFile)
    }

    override fun delMaterialStorage(courseId: String, name: String, onResult: (Throwable?) -> Unit) {
        mStorage.reference.child(Constants.MATERIALS).child(courseId).child(name).delete().addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun delMaterialDatabase(courseId: String, material: Material, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.MATERIALS).child(material.id)
            .removeValue().addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun delMaterialsStorage(courseId: String, onResult: (Throwable?) -> Unit) {
        mStorage.reference.child(Constants.MATERIALS).child(courseId).delete().addOnCompleteListener {
            onResult(it.exception)
        }
    }

    // Quizzes

    override fun saveQuiz(courseId: String, quiz: Quiz, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quiz.id)
            .setValue(quiz).addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun addQuestions(courseId: String, quizId: String, quiz: MutableList<Question>, onResult: (Throwable?) -> Unit) {
        val childUpdates = hashMapOf<String, Any>(
            "/$quizId/${Constants.QUESTIONS}" to quiz
        )
        courseDatabaseReference.child(courseId).child(Constants.Quizzes)
            .updateChildren(childUpdates).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun updateQuestion(courseId: String, quizId: String, questionNo: Int, question: Question, onResult: (Throwable?) -> Unit) {
        val questionValues = question.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/${Constants.QUESTIONS}/${questionNo}" to questionValues
        )
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId)
            .updateChildren(childUpdates).addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun getQuizzes(courseId: String, hashMap: (HashMap<String, Int?>) -> Unit, quizzes: (List<Quiz>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val quizzesList: MutableList<Quiz> = mutableListOf()
                val hashMapList: HashMap<String, Int?> = HashMap()
                for (quiz in snapshot.children) {
                    val quizItem = quiz.getValue(Quiz::class.java)
                    quizzesList.add(quizItem!!)

                    getDegree(courseId, quizItem.id, getUid(), { degree ->
                        hashMapList[quizItem.id] = degree.toInt()
                        hashMap(hashMapList)
                    }) { error ->
                        if (error != null) {
                            onResult(error)
                        }
                    }

                }
                quizzes(quizzesList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(error)
            }
        })
    }

    override fun getDegree(courseId: String, quizId: String, userId: String, degree: (Double) -> Unit,onResult: (DatabaseError?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId)
            .child(Constants.STUDENTS).child(userId)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val firebaseDegree = snapshot.getValue(Double::class.java)
                if (firebaseDegree != null) {
                    degree(firebaseDegree)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(error)
            }
        })
    }

    override fun delQuiz(courseId: String, quizId: String, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId).removeValue()
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun getStudentsInQuiz(
        courseId: String,
        quizId: String,
        hashMap: (HashMap<User, Double>) -> Unit,
        students: (List<User>) -> Unit,
        onResult: (DatabaseError?) -> Unit
    ) {
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId)
            .child(Constants.STUDENTS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val studentsList: MutableList<User> = mutableListOf()
                val hashMapList: HashMap<User, Double> = HashMap()
                for (studentsId in dataSnapshot.children) {
                    val studentIdItem = studentsId.key
                    val studentDegreeItem = studentsId.getValue(Double::class.java)

                    getUserById(studentIdItem!!).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val student = snapshot.getValue(User::class.java)!!
                            studentsList.add(student)
                            hashMapList[student] = studentDegreeItem!!
                            hashMap(hashMapList)
                            students(studentsList)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            onResult(error)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(error)
            }
        })
    }

    override fun saveDegree(courseId: String, quizId: String, degree: Int, noOfQuestions: Int, onResult: (Throwable?) -> Unit) {
        val percentDegree: Double = 100.0 * degree / noOfQuestions
        courseDatabaseReference.child(courseId).child(Constants.Quizzes).child(quizId)
            .child(Constants.STUDENTS).child(getUid()).setValue(percentDegree)
            .addOnCompleteListener {
            onResult(it.exception)
        }
    }

    // Chats

    override fun sendMessageGroup(courseId: String, message: Message, onResult: (Throwable?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.CHATS).child(message.time)
            .setValue(message).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun sendMessagePrivate(user: User, anotherUser: User, message: Message, onResult: (Throwable?) -> Unit) {
        userDatabaseReference.child(user.id).child(Constants.CHATS).child(anotherUser.id)
            .child(message.time).setValue(message).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun getGroupChat(courseId: String, messages: (List<Message>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        courseDatabaseReference.child(courseId).child(Constants.CHATS)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messagesList: MutableList<Message> = mutableListOf()
                for (message in snapshot.children) {
                    val messageItem = message.getValue(Message::class.java)
                    messagesList.add(messageItem!!)
                }
                messages(messagesList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(error)
            }
        })
    }

    override fun getPrivateChat(user: User, anotherUser: User, messages: (List<Message>) -> Unit, onResult: (DatabaseError?) -> Unit) {
        userDatabaseReference.child(user.id).child(Constants.CHATS).child(anotherUser.id)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messagesList: MutableList<Message> = mutableListOf()
                for (message in snapshot.children) {
                    val messageItem = message.getValue(Message::class.java)
                    messagesList.add(messageItem!!)
                }
                messages(messagesList)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(error)
            }
        })
    }
}