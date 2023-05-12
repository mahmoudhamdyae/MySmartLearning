package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MaterialsFragmentArgs(
  public val courseId: String?
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("courseId", this.courseId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("courseId", this.courseId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MaterialsFragmentArgs {
      bundle.setClassLoader(MaterialsFragmentArgs::class.java.classLoader)
      val __courseId : String?
      if (bundle.containsKey("courseId")) {
        __courseId = bundle.getString("courseId")
      } else {
        throw IllegalArgumentException("Required argument \"courseId\" is missing and does not have an android:defaultValue")
      }
      return MaterialsFragmentArgs(__courseId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): MaterialsFragmentArgs {
      val __courseId : String?
      if (savedStateHandle.contains("courseId")) {
        __courseId = savedStateHandle["courseId"]
      } else {
        throw IllegalArgumentException("Required argument \"courseId\" is missing and does not have an android:defaultValue")
      }
      return MaterialsFragmentArgs(__courseId)
    }
  }
}
