package jjh.data.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore by preferencesDataStore(
  name = "TOKEN_MANAGER"
)