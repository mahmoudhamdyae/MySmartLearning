package com.mahmoudhamdyae.smartlearning.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.ui.courses.CoursesAdapter

@BindingAdapter("coursesData")
fun bindCoursesRecyclerView(recyclerView: RecyclerView, data: List<Course>?) {
    val adapter = recyclerView.adapter as CoursesAdapter
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