package com.mahmoudhamdyae.smartlearning.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibilityView")
fun visibilityOfView(view: View, visibility: Boolean) {
    if (visibility) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}