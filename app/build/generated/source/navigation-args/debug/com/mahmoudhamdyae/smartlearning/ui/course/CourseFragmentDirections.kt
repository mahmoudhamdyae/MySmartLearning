package com.mahmoudhamdyae.smartlearning.ui.course

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.`data`.models.Course
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Suppress

public class CourseFragmentDirections private constructor() {
  private data class ActionCourseFragmentToAddStudentFragment(
    public val course: Course?
  ) : NavDirections {
    public override val actionId: Int = R.id.action_courseFragment_to_addStudentFragment

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
        return result
      }
  }

  private data class ActionCourseFragmentToQuizFragment(
    public val course: Course?,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_courseFragment_to_quizFragment

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

  private data class ActionCourseFragmentToMaterialsFragment(
    public val courseId: String?
  ) : NavDirections {
    public override val actionId: Int = R.id.action_courseFragment_to_materialsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("courseId", this.courseId)
        return result
      }
  }

  private data class ActionCourseFragmentToGroupChatFragment(
    public val course: Course?,
    public val isGroup: Boolean,
    public val user: User,
    public val anotherUser: User? = null
  ) : NavDirections {
    public override val actionId: Int = R.id.action_courseFragment_to_groupChatFragment

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
        result.putBoolean("isGroup", this.isGroup)
        if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
          result.putParcelable("user", this.user as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
          result.putSerializable("user", this.user as Serializable)
        } else {
          throw UnsupportedOperationException(User::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
          result.putParcelable("anotherUser", this.anotherUser as Parcelable?)
        } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
          result.putSerializable("anotherUser", this.anotherUser as Serializable?)
        }
        return result
      }
  }

  private data class ActionCourseFragmentToPrivateChatFragment(
    public val course: Course?,
    public val user: User,
    public val teacher: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_courseFragment_to_privateChatFragment

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
        if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
          result.putParcelable("teacher", this.teacher as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
          result.putSerializable("teacher", this.teacher as Serializable)
        } else {
          throw UnsupportedOperationException(User::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
      }
  }

  private data class ActionCourseFragmentToChatFragment(
    public val course: Course?,
    public val isGroup: Boolean,
    public val user: User,
    public val anotherUser: User? = null
  ) : NavDirections {
    public override val actionId: Int = R.id.action_courseFragment_to_chatFragment

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
        result.putBoolean("isGroup", this.isGroup)
        if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
          result.putParcelable("user", this.user as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
          result.putSerializable("user", this.user as Serializable)
        } else {
          throw UnsupportedOperationException(User::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (Parcelable::class.java.isAssignableFrom(User::class.java)) {
          result.putParcelable("anotherUser", this.anotherUser as Parcelable?)
        } else if (Serializable::class.java.isAssignableFrom(User::class.java)) {
          result.putSerializable("anotherUser", this.anotherUser as Serializable?)
        }
        return result
      }
  }

  public companion object {
    public fun actionCourseFragmentToAddStudentFragment(course: Course?): NavDirections =
        ActionCourseFragmentToAddStudentFragment(course)

    public fun actionCourseFragmentToQuizFragment(course: Course?, user: User): NavDirections =
        ActionCourseFragmentToQuizFragment(course, user)

    public fun actionCourseFragmentToMaterialsFragment(courseId: String?): NavDirections =
        ActionCourseFragmentToMaterialsFragment(courseId)

    public fun actionCourseFragmentToGroupChatFragment(
      course: Course?,
      isGroup: Boolean,
      user: User,
      anotherUser: User? = null
    ): NavDirections = ActionCourseFragmentToGroupChatFragment(course, isGroup, user, anotherUser)

    public fun actionCourseFragmentToPrivateChatFragment(
      course: Course?,
      user: User,
      teacher: User
    ): NavDirections = ActionCourseFragmentToPrivateChatFragment(course, user, teacher)

    public fun actionCourseFragmentToChatFragment(
      course: Course?,
      isGroup: Boolean,
      user: User,
      anotherUser: User? = null
    ): NavDirections = ActionCourseFragmentToChatFragment(course, isGroup, user, anotherUser)
  }
}
