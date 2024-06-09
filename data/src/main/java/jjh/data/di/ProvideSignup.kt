package jjh.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.data.remote.RetrofitService
import jjh.data.repository.SignupRepositoryImpl
import jjh.domain.SignupRepository

@Module
@InstallIn(SingletonComponent::class)
object ProvideSignup {


  @Provides
  fun provideSignupRepository(
    retrofitService: RetrofitService,
  ): SignupRepository = SignupRepositoryImpl(retrofitService)
}