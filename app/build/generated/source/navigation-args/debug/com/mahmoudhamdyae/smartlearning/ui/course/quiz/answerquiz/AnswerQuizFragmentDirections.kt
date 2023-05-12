package com.mahmoudhamdyae.smartlearning.ui.course.quiz.answerquiz

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.String
import kotlin.Suppress

public class AnswerQuizFragmentDirections private constructor() {
  private data class ActionAnswerQuizFragmentToQuizStatisticsFragment(
    public val courseName: String,
    public val quizName: String,
    public val degree: Int,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_answerQuizFragment_to_quizStatisticsFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
  }

  public companion object {
    public fun actionAnswerQuizFragmentToQuizStatisticsFragment(
      courseName: String,
      quizName: String,
      degree: Int,
      user: User
    ): NavDirections = ActionAnswerQuizFragmentToQuizStatisticsFragment(courseName, quizName,
        degree, user)
  }
}
