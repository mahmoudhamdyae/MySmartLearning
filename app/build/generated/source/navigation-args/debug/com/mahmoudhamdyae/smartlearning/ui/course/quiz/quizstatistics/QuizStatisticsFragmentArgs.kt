package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizstatistics

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class QuizStatisticsFragmentArgs(
  public val courseName: String,
  public val quizName: String,
  public val degree: Int,
  public val user: User
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("courseName", this.courseName)
    result.putString("quizName", this.quizName)
    result.putInt("degree", this.degree)
    if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
      result.putParcelable("user", this.user as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
      result.putSerializable("user", this.user as Serializable)
    } else {
      throw UnsupportedOperationException(User::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("courseName", this.courseName)
    result.set("quizName", this.quizName)
    result.set("degree", this.degree)
    if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
      result.set("user", this.user as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
      result.set("user", this.user as Serializable)
    } else {
      throw UnsupportedOperationException(User::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): QuizStatisticsFragmentArgs {
      bundle.setClassLoader(QuizStatisticsFragmentArgs::class.java.classLoader)
      val __courseName : String?
      if (bundle.containsKey("courseName")) {
        __courseName = bundle.getString("courseName")
        if (__courseName == null) {
          throw IllegalArgumentException("Argument \"courseName\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"courseName\" is missing and does not have an android:defaultValue")
      }
      val __quizName : String?
      if (bundle.containsKey("quizName")) {
        __quizName = bundle.getString("quizName")
        if (__quizName == null) {
          throw IllegalArgumentException("Argument \"quizName\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"quizName\" is missing and does not have an android:defaultValue")
      }
      val __degree : Int
      if (bundle.containsKey("degree")) {
        __degree = bundle.getInt("degree")
      } else {
        throw IllegalArgumentException("Required argument \"degree\" is missing and does not have an android:defaultValue")
      }
      val __user : User?
      if (bundle.containsKey("user")) {
        if (Parcelable::class.java.isAssignableFrom(User::class.java) ||
            Serializable::class.java.isAssignableFrom(User::class.java)) {
          __user = bundle.get("user") as User?
        } else {
          throw UnsupportedOperationException(User::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__user == null) {
          throw IllegalArgumentException("Argument \"user\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"user\" is missing and does not have an android:defaultValue")
      }
      return QuizStatisticsFragmentArgs(__courseName, __quizName, __degree, __user)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        QuizStatisticsFragmentArgs {
      val __courseName : String?
      if (savedStateHandle.contains("courseName")) {
        __courseName = savedStateHandle["courseName"]
        if (__courseName == null) {
          throw IllegalArgumentException("Argument \"courseName\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"courseName\" is missing and does not have an android:defaultValue")
      }
      val __quizName : String?
      if (savedStateHandle.contains("quizName")) {
        __quizName = savedStateHandle["quizName"]
        if (__quizName == null) {
          throw IllegalArgumentException("Argument \"quizName\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"quizName\" is missing and does not have an android:defaultValue")
      }
      val __degree : Int?
      if (savedStateHandle.contains("degree")) {
        __degree = savedStateHandle["degree"]
        if (__degree == null) {
          throw IllegalArgumentException("Argument \"degree\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"degree\" is missing and does not have an android:defaultValue")
      }
      val __user : User?
      if (savedStateHandle.contains("user")) {
        if (Parcelable::class.java.isAssignableFrom(User::class.java) ||
            Serializable::class.java.isAssignableFrom(User::class.java)) {
          __user = savedStateHandle.get<User?>("user")
        } else {
          throw UnsupportedOperationException(User::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__user == null) {
          throw IllegalArgumentException("Argument \"user\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"user\" is missing and does not have an android:defaultValue")
      }
      return QuizStatisticsFragmentArgs(__courseName, __quizName, __degree, __user)
    }
  }
}
