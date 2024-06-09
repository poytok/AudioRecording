package jjh.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.data.remote.RetrofitService
import jjh.data.repository.SignInRepositoryImpl
import jjh.domain.SignIpRepository

@Module
@InstallIn(SingletonComponent::class)
object ProvideSignIn {


  @Provides
  fun provideSignIpRepository(
    retrofitService: RetrofitService,
  ): SignIpRepository = SignInRepositoryImpl(retrofitService)
}