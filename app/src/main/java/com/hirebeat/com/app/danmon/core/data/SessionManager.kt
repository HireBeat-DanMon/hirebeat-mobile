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
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Base64

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hirebeat_prefs")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    val authToken: Flow<String?> = dataStore.data.map { it[AUTH_TOKEN] }

    val userRoleName: Flow<String?> = authToken.map { token ->
        if (token.isNullOrEmpty()) return@map null
        try {
            val parts = token.split(".")
            if (parts.size < 2) return@map null
            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val json = JSONObject(payload)
            json.optString("role", "GUEST")
        } catch (e: Exception) {
            "GUEST"
        }
    }

    suspend fun saveSession(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN)
        }
    }
}