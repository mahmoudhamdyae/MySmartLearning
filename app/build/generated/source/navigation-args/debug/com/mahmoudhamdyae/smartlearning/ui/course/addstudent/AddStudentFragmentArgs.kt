package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.mahmoudhamdyae.smartlearning.`data`.models.Course
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class AddStudentFragmentArgs(
  public val course: Course?
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
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): AddStudentFragmentArgs {
      bundle.setClassLoader(AddStudentFragmentArgs::class.java.classLoader)
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
      return AddStudentFragmentArgs(__course)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AddStudentFragmentArgs {
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
      return AddStudentFragmentArgs(__course)
    }
  }
}
