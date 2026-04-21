package com.hirebeat.com.app.danmon.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hirebeat_prefs")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val ROLE_ID = stringPreferencesKey("role_id")
        private const val MUSICIAN_ROLE_ID = "22222222-2222-2222-2222-222222222222"
    }

    val authToken: Flow<String?> = dataStore.data.map { it[AUTH_TOKEN] }

    val isMusician: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ROLE_ID] == MUSICIAN_ROLE_ID
    }

    suspend fun saveSession(token: String, roleId: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
            preferences[ROLE_ID] = roleId
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN)
            preferences.remove(ROLE_ID)
        }
    }
}