package com.mahmoudhamdyae.smartlearning.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mahmoudhamdyae.smartlearning.utils.Constants
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import dagger.hilt.android.AndroidEntryPoint

/**
 * Base Fragment to observe on the common LiveData objects
 */
@AndroidEntryPoint
abstract class BaseFragment: Fragment() {

    /**
     * Every fragment has to have an instance of a view model that extends from the BaseViewModel
     */
    abstract val viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    protected fun getUserType() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val isTeacher = sharedPref.getBoolean(Constants.IS_TEACHER, false)
        viewModel.setIsTeacher(if (isTeacher) IsTeacher.TEACHER else IsTeacher.STUDENT)
    }
}