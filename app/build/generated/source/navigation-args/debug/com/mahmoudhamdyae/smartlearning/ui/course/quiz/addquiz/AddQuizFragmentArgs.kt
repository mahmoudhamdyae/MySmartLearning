package com.mahmoudhamdyae.smartlearning.ui.course.quiz.addquiz

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.mahmoudhamdyae.smartlearning.`data`.models.Quiz
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class AddQuizFragmentArgs(
  public val quiz: Quiz,
  public val courseId: String,
  public val addType: Int
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
    result.putString("courseId", this.courseId)
    result.putInt("addType", this.addType)
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
    result.set("courseId", this.courseId)
    result.set("addType", this.addType)
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): AddQuizFragmentArgs {
      bundle.setClassLoader(AddQuizFragmentArgs::class.java.classLoader)
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
      val __courseId : String?
      if (bundle.containsKey("courseId")) {
        __courseId = bundle.getString("courseId")
        if (__courseId == null) {
          throw IllegalArgumentException("Argument \"courseId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"courseId\" is missing and does not have an android:defaultValue")
      }
      val __addType : Int
      if (bundle.containsKey("addType")) {
        __addType = bundle.getInt("addType")
      } else {
        throw IllegalArgumentException("Required argument \"addType\" is missing and does not have an android:defaultValue")
      }
      return AddQuizFragmentArgs(__quiz, __courseId, __addType)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): AddQuizFragmentArgs {
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
      val __courseId : String?
      if (savedStateHandle.contains("courseId")) {
        __courseId = savedStateHandle["courseId"]
        if (__courseId == null) {
          throw IllegalArgumentException("Argument \"courseId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"courseId\" is missing and does not have an android:defaultValue")
      }
      val __addType : Int?
      if (savedStateHandle.contains("addType")) {
        __addType = savedStateHandle["addType"]
        if (__addType == null) {
          throw IllegalArgumentException("Argument \"addType\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"addType\" is missing and does not have an android:defaultValue")
      }
      return AddQuizFragmentArgs(__quiz, __courseId, __addType)
    }
  }
}
