package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.`data`.models.Course
import com.mahmoudhamdyae.smartlearning.`data`.models.Quiz
import com.mahmoudhamdyae.smartlearning.`data`.models.User
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.String
import kotlin.Suppress

public class QuizFragmentDirections private constructor() {
  private data class ActionQuizFragmentToAddQuizFragment(
    public val quiz: Quiz,
    public val courseId: String,
    public val addType: Int
  ) : NavDirections {
    public override val actionId: Int = R.id.action_quizFragment_to_addQuizFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
  }

  private data class ActionQuizFragmentToAnswerQuizFragment(
    public val quiz: Quiz,
    public val course: Course,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_quizFragment_to_answerQuizFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
  }

  private data class ActionQuizFragmentToQuizDetailsFragment(
    public val quiz: Quiz,
    public val course: Course
  ) : NavDirections {
    public override val actionId: Int = R.id.action_quizFragment_to_quizDetailsFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
        return result
      }
  }

  private data class ActionQuizFragmentToQuizStatisticsFragment(
    public val courseName: String,
    public val quizName: String,
    public val degree: Int,
    public val user: User
  ) : NavDirections {
    public override val actionId: Int = R.id.action_quizFragment_to_quizStatisticsFragment

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
    public fun actionQuizFragmentToAddQuizFragment(
      quiz: Quiz,
      courseId: String,
      addType: Int
    ): NavDirections = ActionQuizFragmentToAddQuizFragment(quiz, courseId, addType)

    public fun actionQuizFragmentToAnswerQuizFragment(
      quiz: Quiz,
      course: Course,
      user: User
    ): NavDirections = ActionQuizFragmentToAnswerQuizFragment(quiz, course, user)

    public fun actionQuizFragmentToQuizDetailsFragment(quiz: Quiz, course: Course): NavDirections =
        ActionQuizFragmentToQuizDetailsFragment(quiz, course)

    public fun actionQuizFragmentToQuizStatisticsFragment(
      courseName: String,
      quizName: String,
      degree: Int,
      user: User
    ): NavDirections = ActionQuizFragmentToQuizStatisticsFragment(courseName, quizName, degree,
        user)
  }
}
