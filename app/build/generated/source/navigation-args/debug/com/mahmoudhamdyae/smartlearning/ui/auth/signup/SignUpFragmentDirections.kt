package com.mahmoudhamdyae.smartlearning.ui.auth.signup

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R

public class SignUpFragmentDirections private constructor() {
  public companion object {
    public fun actionSignUpFragmentToMainFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signUpFragment_to_mainFragment)
  }
}
