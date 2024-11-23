package com.bangkit.capstone.agreaseapp.data.repository

import android.util.Log
import com.bangkit.capstone.agreaseapp.data.local.preference.UserPreference
import com.bangkit.capstone.agreaseapp.data.model.UserModel
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
    private val auth : FirebaseAuth
) {
    fun login(email: String, password: String) = flow {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                saveUser(
                    id = currentUser.uid,
                    email = currentUser.email ?: "",
                    name = currentUser.displayName ?: "User",
                    profile = "",
                    token = "mytoken"
                )
                emit(
                    TemplateResponse(
                        success = true,
                        message = "Login successful",
                        data = UserModel(currentUser.uid, currentUser.email ?: "", currentUser.displayName ?: "User", "", "mytoken")
                    )
                )
            } else {
                emit(
                    TemplateResponse(
                        success = false,
                        message = "User not found",
                        data = UserModel("", "", "", "", "")
                    )
                )
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthException -> {
                    when (e.errorCode) {
                        "ERROR_INVALID_CREDENTIAL" -> "The email or password is incorrect. Please try again."
                        "ERROR_USER_NOT_FOUND" -> "There is no user corresponding to this email."
                        "ERROR_INVALID_EMAIL" -> "The email address is not valid."
                        "ERROR_USER_DISABLED" -> "The user account has been disabled by an administrator."
                        "ERROR_TOO_MANY_REQUESTS" -> "Too many login attempts. Please try again later."
                        else -> "Authentication failed. Please try again."
                    }
                }
                else -> "An unexpected error occurred. Please try again."
            }
            emit(
                TemplateResponse(
                    success = false,
                    message = errorMessage,
                    data = UserModel("", "", "", "", "")
                )
            )
        }
    }

    fun register(email: String, password: String) = flow {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                saveUser(
                    id = currentUser.uid,
                    email = currentUser.email ?: "",
                    name = currentUser.displayName ?: "User",
                    profile = "",
                    token = "mytoken"
                )
                emit(
                    TemplateResponse(
                        success = true,
                        message = "Registration successful",
                        data = UserModel(currentUser.uid, currentUser.email ?: "", currentUser.displayName ?: "User", "", "mytoken")
                    )
                )
            } else {
                emit(
                    TemplateResponse(
                        success = false,
                        message = "User not created",
                        data = UserModel("", "", "", "", "")
                    )
                )
            }
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is FirebaseAuthException -> {
                    Log.d("UserRepository", "Login: ${e.errorCode}")
                    when (e.errorCode) {
                        "ERROR_EMAIL_ALREADY_IN_USE" -> "The email address is already in use by another account."
                        "ERROR_INVALID_EMAIL" -> "The email address is badly formatted."
                        "ERROR_WEAK_PASSWORD" -> "The password is too weak. Please choose a stronger password."
                        "ERROR_OPERATION_NOT_ALLOWED" -> "This operation is not allowed. Please contact support."
                        else -> "Authentication failed. Please try again."
                    }
                }
                else -> "An unexpected error occurred. Please try again."
            }
            emit(
                TemplateResponse(
                    success = false,
                    message = errorMessage,
                    data = UserModel("", "", "", "", "")
                )
            )
        }
    }

    fun getUser(): Flow<TemplateResponse<UserModel>> {
        val userPreference = runBlocking {
            userPreference.getUser().first()
        }

        return flow {
            val user = apiService.getUser(token = userPreference.token)
            if (!user.isSuccessful) {
                val message = user.processError()
                if (message == "Unauthorized") {
                    logOut()
                }

                emit(
                    TemplateResponse(
                        success = false,
                        message = message,
                        data = UserModel("", "", "", "", "")
                    )
                )
                return@flow
            }

            user.body()?.apply {
                emit(this)
            }
        }.catch { e ->
            emit(
                TemplateResponse(
                    success = false,
                    message = e.message.toString(),
                    data = UserModel("", "", "", "", "")
                )
            )
        }
    }

    fun getUserPreference(): Flow<UserModel> = userPreference.getUser()

    suspend fun getToken(): String = userPreference.getUser().first().token

    suspend fun saveUser(id: String, email: String, name: String, profile: String, token: String) {
        val user = UserModel(id, name, email, profile, token)
        userPreference.saveUser(user)
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
//
//    fun changePassword(newPassword: String, confirmPassword: String) = flow {
//        val userPreference = userPreference.getUser().first()
//        val user = apiService.changePassword(
//            token = userPreference.token,
//            password = newPassword,
//            confirm_password = confirmPassword
//        )
//        if (!user.isSuccessful) {
//            val message = user.processError()
//
//            emit(
//                TemplateResponse(
//                    success = false,
//                    message = message,
//                    data = false
//                )
//            )
//            return@flow
//        }
//
//        user.body()?.apply {
//            emit(this)
//        }
//    }.catch { e ->
//        emit(
//            TemplateResponse(
//                success = false,
//                message = e.message.toString(),
//                data = false
//            )
//        )
//    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
            auth : FirebaseAuth
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService, auth)
            }.also {
                instance = it
            }
    }
}