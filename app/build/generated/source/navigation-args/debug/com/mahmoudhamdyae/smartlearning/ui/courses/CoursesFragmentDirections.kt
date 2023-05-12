package com.mahmoudhamdyae.smartlearning.ui.courses

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.`data`.models.Course
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class CoursesFragmentDirections private constructor() {
  private data class ActionCoursesFragmentToSearchFragment(
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_coursesFragment_to_searchFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
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
  }

  private data class ActionCoursesFragmentToProfileFragment(
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_coursesFragment_to_profileFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
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
  }

  private data class ActionCoursesFragmentToCourseFragment(
    public val course: Course?,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_coursesFragment_to_courseFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
  }

  public companion object {
    public fun actionCoursesFragmentToLogInFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_coursesFragment_to_logInFragment)

    public fun actionCoursesFragmentToSearchFragment(user: User): NavDirections =
        ActionCoursesFragmentToSearchFragment(user)

    public fun actionCoursesFragmentToProfileFragment(user: User): NavDirections =
        ActionCoursesFragmentToProfileFragment(user)

    public fun actionCoursesFragmentToCourseFragment(course: Course?, user: User): NavDirections =
        ActionCoursesFragmentToCourseFragment(course, user)

    public fun actionCoursesFragmentToNotificationFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_coursesFragment_to_notificationFragment)
  }
}
