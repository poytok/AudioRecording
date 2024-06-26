package jjh.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jjh.data.util.TokenManager
import jjh.data.util.dataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideTokenManager {


  @Provides
  @Singleton
  fun provideTokenManager(
    dataStore: DataStore<Preferences>,
  ): TokenManager = TokenManager(dataStore)

  @Provides
  fun provideDataStore(
    @ApplicationContext context: Context,
  ): DataStore<Preferences> = context.dataStore


}