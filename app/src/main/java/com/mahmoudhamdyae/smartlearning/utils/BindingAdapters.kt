package com.mahmoudhamdyae.smartlearning.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.*
import com.mahmoudhamdyae.smartlearning.ui.course.addstudent.StudentsAdapter
import com.mahmoudhamdyae.smartlearning.ui.course.chat.ChatAdapter
import com.mahmoudhamdyae.smartlearning.ui.course.materials.MaterialsAdapter
import com.mahmoudhamdyae.smartlearning.ui.course.quiz.QuizAdapter
import com.mahmoudhamdyae.smartlearning.ui.courses.CoursesAdapter
import com.mahmoudhamdyae.smartlearning.ui.notification.NotificationAdapter

@BindingAdapter("coursesData")
fun bindCoursesRecyclerView(recyclerView: RecyclerView, data: List<Course>?) {
    val adapter = recyclerView.adapter as CoursesAdapter
    adapter.submitList(data)
}

@BindingAdapter("studentsData")
fun bindStudentsRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as StudentsAdapter
    adapter.submitList(data)
}

@BindingAdapter("materialsData")
fun bindMaterialsRecyclerView(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as MaterialsAdapter
    adapter.submitList(data)
}

@BindingAdapter("notificationsData")
fun bindNotificationsRecyclerView(recyclerView: RecyclerView, data: List<Notification>?) {
    val adapter = recyclerView.adapter as NotificationAdapter
    adapter.submitList(data)
}

@BindingAdapter("quizzesData")
fun bindQuizzesRecyclerView(recyclerView: RecyclerView, data: List<Quiz>?) {
    val adapter = recyclerView.adapter as QuizAdapter
    adapter.submitList(data)
}

@BindingAdapter("chatData")
fun bindChatRecyclerView(recyclerView: RecyclerView, data: List<Message>?) {
    val adapter = recyclerView.adapter as ChatAdapter
    adapter.submitList(data)
}

@BindingAdapter("visibilityOfLoading")
fun visibilityOfProgressBar(progressBar: ProgressBar, status: STATUS?) {
    if (status == STATUS.LOADING) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}

@BindingAdapter("visibleIfTeacher")
fun visibleIfTeacher(view: View, isTeacher: IsTeacher?) {
    if (isTeacher == IsTeacher.TEACHER) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("bindEmptyView")
fun bindEmptyView(view: View, data: List<Any>?) {
    if (data.isNullOrEmpty()) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}