package com.bangkit.capstone.agreaseapp.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.VerifyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val authEmail = stringPreferencesKey("authEmail")
    private val authUID = stringPreferencesKey("authUID")

    private val uid = stringPreferencesKey("uid")
    private val email = stringPreferencesKey("email")
    private val nama = stringPreferencesKey("nama")
    private val phone = stringPreferencesKey("phone")
    private val address = stringPreferencesKey("address")
    private val role = stringPreferencesKey("role")
    private val photo = stringPreferencesKey("photo")
    private val isVerified = booleanPreferencesKey("isVerified")

    suspend fun saveUser(user: UserModel) {
        dataStore.edit {
            it[uid] = user.uid
            it[email] = user.email
            it[nama] = user.nama
            it[phone] = user.phone
            it[address] = user.address
            it[role] = user.role
            it[photo] = user.photo
            it[isVerified] = user.isVerified
        }
    }

    suspend fun saveVerify(auth: VerifyModel) {
        dataStore.edit {
            it[authUID] = auth.authUID
            it[authEmail] = auth.authEmail
        }
    }

    fun getUser(): Flow<UserModel> = dataStore.data.map {
        UserModel(
            it[uid] ?: "",
            it[email] ?: "",
            it[nama] ?: "",
            it[phone] ?: "",
            it[address] ?: "",
            it[role] ?: "",
            it[photo] ?: "",
            it[isVerified] ?: false
        )
    }

    fun getVerify(): Flow<VerifyModel> = dataStore.data.map {
        VerifyModel(
            it[authEmail] ?: "",
            it[authUID] ?: ""
        )
    }

    suspend fun destroyUser() = dataStore.edit { it.clear() }

    suspend fun destroyVerify() = dataStore.edit {
        it[authEmail] = ""
        it[authUID] = ""
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}