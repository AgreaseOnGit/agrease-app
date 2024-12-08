package com.bangkit.capstone.agreaseapp.data.repository

import com.bangkit.capstone.agreaseapp.data.local.preference.UserPreference
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.VerifyModel
import com.bangkit.capstone.agreaseapp.data.remote.response.RegisterResponse
import com.bangkit.capstone.agreaseapp.data.remote.response.TemplateResponse
import com.bangkit.capstone.agreaseapp.data.remote.retrofit.ApiService
import com.bangkit.capstone.agreaseapp.utils.processError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {
    fun login(email: String, password: String) = flow {
        val login = apiService.login(email, password)
        if (!login.isSuccessful) {
            val message = login.processError()

            emit(
                TemplateResponse(
                    success = false,
                    message = message,
                    data = UserModel("", "", "", "", "", "", "", false)
                )
            )
            return@flow
        }

        login.body()?.let {
            saveUser(it.data.uid, it.data.email, it.data.nama, it.data.phone, it.data.address, it.data.role, it.data.photo, it.data.isVerified)
            emit(it)
        }
    }.catch { e ->
        emit(
            TemplateResponse(
                success = false,
                message = e.message.toString(),
                data = UserModel("", "", "", "", "", "", "", false)
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
            saveVerifyUID(email, this.uid)
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
        val userid = userPreference.getVerify().first()
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

    fun getUserPreference(): Flow<UserModel> {
        return userPreference.getUser()
    }

    suspend fun getVerified(): Boolean = userPreference.getUser().first().isVerified

    suspend fun getVerifyUID(): VerifyModel = userPreference.getVerify().first()

    suspend fun saveUser(uid: String, email: String, nama: String, phone: String, address: String, role: String, photo: String, isVerified: Boolean) {
        val user = UserModel(uid, email, nama, phone, address, role, photo, isVerified)
        userPreference.saveUser(user)
    }
    fun saveDummyUserLogin()= flow {
        val user = UserModel("user01", "user01@gmail.com", "Mr. Vincent", "081234567890", "Jl. Merdeka No 7, Semarang, Jawa Tengah", "buyer", "https://static.wikia.nocookie.net/character-stats-and-profiles/images/7/71/Pak_Vincent.png/revision/latest?cb=20241115022726", true)
        saveUser(user.uid, user.email, user.nama, user.phone, user.address, user.role, user.photo, user.isVerified)
        emit(user)
    }

    suspend fun saveVerifyUID(authEmail: String, authUID: String) {
        val auth = VerifyModel(authEmail, authUID)
        userPreference.saveVerify(auth)
    }

    suspend fun logOut(): Boolean {
        userPreference.destroyUser()
        return true
    }

    suspend fun destroyVerifyUID(): Boolean {
        userPreference.destroyVerify()
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