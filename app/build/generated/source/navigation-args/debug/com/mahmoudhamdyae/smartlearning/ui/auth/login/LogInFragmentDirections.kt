package com.mahmoudhamdyae.smartlearning.ui.auth.login

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R

public class LogInFragmentDirections private constructor() {
  public companion object {
    public fun actionLogInFragmentToSignUpFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_logInFragment_to_signUpFragment)

    public fun actionLogInFragmentToMainFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_logInFragment_to_mainFragment)

    public fun actionLogInFragmentToWelcomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_logInFragment_to_welcomeFragment)
  }
}
