package com.bangkit.capstone.agreaseapp.data.repository

import android.util.Log
import com.bangkit.capstone.agreaseapp.data.local.preference.UserPreference
import com.bangkit.capstone.agreaseapp.data.model.RegisterModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.remote.response.RegisterResponse
import com.bangkit.capstone.agreaseapp.data.remote.response.TemplateResponse
import com.bangkit.capstone.agreaseapp.data.remote.retrofit.ApiService
import com.bangkit.capstone.agreaseapp.utils.processError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    fun login(email: String, password: String) = flow {
        val login = apiService.login(email, password)
        if (!login.isSuccessful) {
            val message = login.processError()
            Log.d("UserRepository", "login: $message")

            emit(
                TemplateResponse(
                    success = false,
                    message = message,
                    data = UserModel("", "", "", "", "", "", false)
                )
            )
            return@flow
        }

        login.body()?.let {
            saveUser(it.data.uid, it.data.email, it.data.nama, it.data.phone, it.data.address, it.data.role, it.data.isVerified)
            emit(it)
        }
    }.catch { e ->
        emit(
            TemplateResponse(
                success = false,
                message = e.message.toString(),
                data = UserModel("", "", "", "", "", "", false)
            )
        )
    }

    fun register( email: String, password: String, displayName: String, phone: String, address: String, role: String) = flow {
        val register = apiService.register( email, password, displayName, phone, address, role)
        if (!register.isSuccessful) {
            val message = register.processError()

            emit(
                RegisterResponse(
                    success = false,
                    message = message,
                    uid = ""
                )
            )
            return@flow
        }

        register.body()?.apply {
            saveUID(uid)
            emit(this)
        }
    }.catch { e ->
        emit(
            RegisterResponse(
                success = false,
                message = e.message.toString(),
                uid = ""
            )
        )
    }

    fun verify( codeOTP: Int) = flow {
        val userid = userPreference.getUID().first()
        val register = apiService.verify( userid.authUID, codeOTP)
        if (!register.isSuccessful) {
            val message = register.processError()

            emit(
                RegisterResponse(
                    success = false,
                    message = message,
                    uid = ""
                )
            )
            return@flow
        }

        register.body()?.apply {
            emit(this)
        }
    }.catch { e ->
        emit(
            RegisterResponse(
                success = false,
                message = e.message.toString(),
                uid = ""
            )
        )
    }

//    fun getUser(): Flow<TemplateResponse<UserModel>> {
//        val userPreference = runBlocking {
//            userPreference.getUser().first()
//        }
//
//        return flow {
//            val user = apiService.getUser(token = userPreference.token)
//            if (!user.isSuccessful) {
//                val message = user.processError()
//                if (message == "Unauthorized") {
//                    logOut()
//                }
//
//                emit(
//                    TemplateResponse(
//                        success = false,
//                        message = message,
//                        data = UserModel("", "", "", "", "")
//                    )
//                )
//                return@flow
//            }
//
//            user.body()?.apply {
//                emit(this)
//            }
//        }.catch { e ->
//            emit(
//                TemplateResponse(
//                    success = false,
//                    message = e.message.toString(),
//                    data = UserModel("", "", "", "", "")
//                )
//            )
//        }
//    }

    fun getUserPreference(): Flow<UserModel> = userPreference.getUser()

    suspend fun getVerified(): Boolean = userPreference.getUser().first().isVerified

    suspend fun getRegisteredUID(): String = userPreference.getUID().first().authUID

    suspend fun saveUser(uid: String, email: String, nama: String, phone: String, address: String, role: String, isVerified: Boolean) {
        val user = UserModel(uid, email, nama, phone, address, role, isVerified)
        userPreference.saveUser(user)
    }

    suspend fun saveUID(authUID: String) {
        val auth = RegisterModel(authUID)
        userPreference.saveUID(auth)
    }

    suspend fun logOut(): Boolean {
        userPreference.destroyUser()

        return true
    }

//    fun updateUser(name: String, profile: File?, email: String) = flow {
//        val userPreference = userPreference.getUser().first()
//
//        val multipartPhoto = if (profile != null) MultipartBody.Part.createFormData(
//            "profile",
//            profile.name,
//            profile.asRequestBody("image/jpeg".toMediaType())
//        ) else null
//
//        val user = apiService.updateUser(
//            token = userPreference.token,
//            profile = multipartPhoto,
//            name = name,
//            email = email,
//        )
//        if (!user.isSuccessful) {
//            val message = user.processError()
//
//            emit(
//                TemplateResponse(
//                    success = false,
//                    message = message,
//                    data = UserModel(0, "", "", "", userPreference.token)
//                )
//            )
//            return@flow
//        }
//
//        user.body()?.apply {
//            saveUser(data.id, data.email, data.name, data.profile ?: "", userPreference.token)
//            emit(this)
//        }
//    }.catch { e ->
//        emit(
//            TemplateResponse(
//                success = false,
//                message = e.message.toString(),
//                data = UserModel(0, "", "", "", "")
//            )
//        )
//    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also {
                instance = it
            }
    }
}