package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.mahmoudhamdyae.smartlearning.`data`.models.Course
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class QuizFragmentArgs(
  public val course: Course?,
  public val user: User
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(Course::class.java)) {
      result.putParcelable("course", this.course as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(Course::class.java)) {
      result.putSerializable("course", this.course as Serializable?)
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
    if (Parcelable::class.java.isAssignableFrom(Course::class.java)) {
      result.set("course", this.course as Parcelable?)
    } else if (Serializable::class.java.isAssignableFrom(Course::class.java)) {
      result.set("course", this.course as Serializable?)
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
    public fun fromBundle(bundle: Bundle): QuizFragmentArgs {
      bundle.setClassLoader(QuizFragmentArgs::class.java.classLoader)
      val __course : Course?
      if (bundle.containsKey("course")) {
        if (Parcelable::class.java.isAssignableFrom(Course::class.java) ||
            Serializable::class.java.isAssignableFrom(Course::class.java)) {
          __course = bundle.get("course") as Course?
        } else {
          throw UnsupportedOperationException(Course::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
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
      return QuizFragmentArgs(__course, __user)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): QuizFragmentArgs {
      val __course : Course?
      if (savedStateHandle.contains("course")) {
        if (Parcelable::class.java.isAssignableFrom(Course::class.java) ||
            Serializable::class.java.isAssignableFrom(Course::class.java)) {
          __course = savedStateHandle.get<Course?>("course")
        } else {
          throw UnsupportedOperationException(Course::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
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
      return QuizFragmentArgs(__course, __user)
    }
  }
}
