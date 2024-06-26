package jjh.preinterview.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jjh.preinterview.tensor.Model
import jjh.preinterview.tensor.VoiceActivityDetection

@Module
@InstallIn(SingletonComponent::class)
object VoiceActivityDetectionModule {

  @Provides
  fun provideVoiceActivityDetection(@ApplicationContext context: Context): VoiceActivityDetection {
    return VoiceActivityDetection(Model.readModel(context.resources))
  }
}