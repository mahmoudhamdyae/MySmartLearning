package com.mahmoudhamdyae.smartlearning.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.ui.course.materials.MaterialsAdapter
import com.mahmoudhamdyae.smartlearning.ui.course.quiz.QuizAdapter
import com.mahmoudhamdyae.smartlearning.ui.courses.CoursesAdapter

@BindingAdapter("coursesData")
fun bindCoursesRecyclerView(recyclerView: RecyclerView, data: List<Course>?) {
    val adapter = recyclerView.adapter as CoursesAdapter
    adapter.submitList(data)
}

@BindingAdapter("materialsData")
fun bindMaterialsRecyclerView(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as MaterialsAdapter
    adapter.submitList(data)
}

@BindingAdapter("quizzesData")
fun bindQuizzesRecyclerView(recyclerView: RecyclerView, data: List<Quiz>?) {
    val adapter = recyclerView.adapter as QuizAdapter
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

@BindingAdapter("visibleIfStudent")
fun visibleIfStudent(view: View, isTeacher: IsTeacher?) {
    if (isTeacher == IsTeacher.STUDENT) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}