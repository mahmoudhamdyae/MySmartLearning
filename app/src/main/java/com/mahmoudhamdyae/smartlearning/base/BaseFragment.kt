package com.mahmoudhamdyae.smartlearning.base

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Base Fragment to observe on the common LiveData objects
 */
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
    }
}