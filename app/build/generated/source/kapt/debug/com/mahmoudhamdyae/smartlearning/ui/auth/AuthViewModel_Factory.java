// Generated by Dagger (https://dagger.dev).
package com.mahmoudhamdyae.smartlearning.ui.auth;

import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<FirebaseRepository> repositoryProvider;

  public AuthViewModel_Factory(Provider<FirebaseRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<FirebaseRepository> repositoryProvider) {
    return new AuthViewModel_Factory(repositoryProvider);
  }

  public static AuthViewModel newInstance(FirebaseRepository repository) {
    return new AuthViewModel(repository);
  }
}