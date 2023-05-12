package com.mahmoudhamdyae.smartlearning.ui.notification

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

public class NotificationFragmentDirections private constructor() {
  private data class ActionNotificationFragmentToCourseFragment(
    public val course: Course?,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notificationFragment_to_courseFragment

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

  private data class ActionNotificationFragmentToPrivateChatFragment(
    public val course: Course?,
    public val user: User,
    public val teacher: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notificationFragment_to_privateChatFragment

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

  private data class ActionNotificationFragmentToGroupChatFragment(
    public val course: Course?,
    public val isGroup: Boolean,
    public val user: User,
    public val anotherUser: User? = null
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notificationFragment_to_groupChatFragment

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

  private data class ActionNotificationFragmentToMaterialsFragment(
    public val courseId: String?
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notificationFragment_to_materialsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("courseId", this.courseId)
        return result
      }
  }

  private data class ActionNotificationFragmentToQuizFragment(
    public val course: Course?,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notificationFragment_to_quizFragment

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

  private data class ActionNotificationFragmentToChatFragment(
    public val course: Course?,
    public val isGroup: Boolean,
    public val user: User,
    public val anotherUser: User? = null
  ) : NavDirections {
    public override val actionId: Int = R.id.action_notificationFragment_to_chatFragment

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
    public fun actionNotificationFragmentToCourseFragment(course: Course?, user: User):
        NavDirections = ActionNotificationFragmentToCourseFragment(course, user)

    public fun actionNotificationFragmentToPrivateChatFragment(
      course: Course?,
      user: User,
      teacher: User
    ): NavDirections = ActionNotificationFragmentToPrivateChatFragment(course, user, teacher)

    public fun actionNotificationFragmentToGroupChatFragment(
      course: Course?,
      isGroup: Boolean,
      user: User,
      anotherUser: User? = null
    ): NavDirections = ActionNotificationFragmentToGroupChatFragment(course, isGroup, user,
        anotherUser)

    public fun actionNotificationFragmentToMaterialsFragment(courseId: String?): NavDirections =
        ActionNotificationFragmentToMaterialsFragment(courseId)

    public fun actionNotificationFragmentToQuizFragment(course: Course?, user: User): NavDirections
        = ActionNotificationFragmentToQuizFragment(course, user)

    public fun actionNotificationFragmentToChatFragment(
      course: Course?,
      isGroup: Boolean,
      user: User,
      anotherUser: User? = null
    ): NavDirections = ActionNotificationFragmentToChatFragment(course, isGroup, user, anotherUser)
  }
}
