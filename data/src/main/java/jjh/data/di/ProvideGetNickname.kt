package jjh.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.data.remote.RetrofitService
import jjh.data.repository.GetNicknameRepositoryImpl
import jjh.domain.GetNicknameRepository

@Module
@InstallIn(SingletonComponent::class)
object ProvideGetNickname {


  @Provides
  fun provideGetNicknameRepository(
    retrofitService: RetrofitService,
  ): GetNicknameRepository = GetNicknameRepositoryImpl(retrofitService)
}