package com.mahmoudhamdyae.smartlearning.ui.course.quiz.answerquiz

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.mahmoudhamdyae.smartlearning.`data`.models.Course
import com.mahmoudhamdyae.smartlearning.`data`.models.Quiz
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class AnswerQuizFragmentArgs(
  public val quiz: Quiz,
  public val course: Course,
  public val user: User
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(Quiz::class.java)) {
      result.putParcelable("quiz", this.quiz as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Quiz::class.java)) {
      result.putSerializable("quiz", this.quiz as Serializable)
    } else {
      throw UnsupportedOperationException(Quiz::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    if (Parcelable::class.java.isAssignableFrom(Course::class.java)) {
      result.putParcelable("course", this.course as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Course::class.java)) {
      result.putSerializable("course", this.course as Serializable)
    } else {
      throw UnsupportedOperationException(Course::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
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
    if (Parcelable::class.java.isAssignableFrom(Quiz::class.java)) {
      result.set("quiz", this.quiz as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Quiz::class.java)) {
      result.set("quiz", this.quiz as Serializable)
    } else {
      throw UnsupportedOperationException(Quiz::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    if (Parcelable::class.java.isAssignableFrom(Course::class.java)) {
      result.set("course", this.course as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Course::class.java)) {
      result.set("course", this.course as Serializable)
    } else {
      throw UnsupportedOperationException(Course::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
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
    public fun fromBundle(bundle: Bundle): AnswerQuizFragmentArgs {
      bundle.setClassLoader(AnswerQuizFragmentArgs::class.java.classLoader)
      val __quiz : Quiz?
      if (bundle.containsKey("quiz")) {
        if (Parcelable::class.java.isAssignableFrom(Quiz::class.java) ||
            Serializable::class.java.isAssignableFrom(Quiz::class.java)) {
          __quiz = bundle.get("quiz") as Quiz?
        } else {
          throw UnsupportedOperationException(Quiz::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__quiz == null) {
          throw IllegalArgumentException("Argument \"quiz\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"quiz\" is missing and does not have an android:defaultValue")
      }
      val __course : Course?
      if (bundle.containsKey("course")) {
        if (Parcelable::class.java.isAssignableFrom(Course::class.java) ||
            Serializable::class.java.isAssignableFrom(Course::class.java)) {
          __course = bundle.get("course") as Course?
        } else {
          throw UnsupportedOperationException(Course::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__course == null) {
          throw IllegalArgumentException("Argument \"course\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"course\" is missing and does not have an android:defaultValue")
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
      return AnswerQuizFragmentArgs(__quiz, __course, __user)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AnswerQuizFragmentArgs {
      val __quiz : Quiz?
      if (savedStateHandle.contains("quiz")) {
        if (Parcelable::class.java.isAssignableFrom(Quiz::class.java) ||
            Serializable::class.java.isAssignableFrom(Quiz::class.java)) {
          __quiz = savedStateHandle.get<Quiz?>("quiz")
        } else {
          throw UnsupportedOperationException(Quiz::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__quiz == null) {
          throw IllegalArgumentException("Argument \"quiz\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"quiz\" is missing and does not have an android:defaultValue")
      }
      val __course : Course?
      if (savedStateHandle.contains("course")) {
        if (Parcelable::class.java.isAssignableFrom(Course::class.java) ||
            Serializable::class.java.isAssignableFrom(Course::class.java)) {
          __course = savedStateHandle.get<Course?>("course")
        } else {
          throw UnsupportedOperationException(Course::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__course == null) {
          throw IllegalArgumentException("Argument \"course\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"course\" is missing and does not have an android:defaultValue")
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
      return AnswerQuizFragmentArgs(__quiz, __course, __user)
    }
  }
}
