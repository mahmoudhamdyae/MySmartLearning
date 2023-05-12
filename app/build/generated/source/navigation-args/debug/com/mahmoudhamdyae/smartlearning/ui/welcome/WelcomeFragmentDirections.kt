package com.mahmoudhamdyae.smartlearning.ui.welcome

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.mahmoudhamdyae.smartlearning.R

public class WelcomeFragmentDirections private constructor() {
  public companion object {
    public fun actionWelcomeFragmentToLogInFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_welcomeFragment_to_logInFragment)
  }
}
