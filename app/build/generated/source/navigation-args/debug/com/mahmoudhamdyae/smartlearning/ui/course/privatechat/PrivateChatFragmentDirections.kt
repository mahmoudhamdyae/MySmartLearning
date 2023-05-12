package com.mahmoudhamdyae.smartlearning.ui.course.privatechat

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
import kotlin.Suppress

public class PrivateChatFragmentDirections private constructor() {
  private data class ActionPrivateChatFragmentToChatFragment(
    public val course: Course?,
    public val isGroup: Boolean,
    public val user: User,
    public val anotherUser: User? = null
  ) : NavDirections {
    public override val actionId: Int = R.id.action_privateChatFragment_to_chatFragment

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
    public fun actionPrivateChatFragmentToChatFragment(
      course: Course?,
      isGroup: Boolean,
      user: User,
      anotherUser: User? = null
    ): NavDirections = ActionPrivateChatFragmentToChatFragment(course, isGroup, user, anotherUser)
  }
}
