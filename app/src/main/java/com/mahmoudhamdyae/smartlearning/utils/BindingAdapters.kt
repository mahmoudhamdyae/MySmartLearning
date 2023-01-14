package com.mahmoudhamdyae.smartlearning.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.ui.courses.CoursesAdapter

@BindingAdapter("visibilityOfLoading")
fun visibilityOfProgressBar(view: View, status: STATUS?) {
    if (status == STATUS.LOADING) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("coursesData")
fun bindCoursesRecyclerView(recyclerView: RecyclerView, data: List<Course>?) {
    val adapter = recyclerView.adapter as CoursesAdapter
    adapter.submitList(data)
}