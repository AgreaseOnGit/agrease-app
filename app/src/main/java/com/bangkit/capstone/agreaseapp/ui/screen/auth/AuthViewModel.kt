package com.bangkit.capstone.agreaseapp.ui.screen.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.VerifyModel
import com.bangkit.capstone.agreaseapp.data.remote.response.RegisterResponse
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel (
    private val userRepository: UserRepository
): ViewModel() {
    private val _user: MutableState<UiState<UserModel>> = mutableStateOf(UiState.Unauthorized)
    val user: MutableState<UiState<UserModel>>
        get() = _user

    private val _auth: MutableState<UiState<RegisterResponse>> = mutableStateOf(UiState.Unauthorized)
    val auth: MutableState<UiState<RegisterResponse>>
        get() = _auth

    private val _isRegistered: MutableState<UiState<Boolean>> = mutableStateOf(UiState.Loading)
    val isRegistered: MutableState<UiState<Boolean>>
        get() = _isRegistered

    private val _verifyData: MutableStateFlow<UiState<VerifyModel>> = MutableStateFlow(UiState.Loading)
    val verifyData: StateFlow<UiState<VerifyModel>>
        get() = _verifyData

    fun login(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()){
            _user.value = UiState.Error("Email or password can't be empty")
            return
        }
        if (email == "user.buyer@gmail.com" || email == "user.seller@gmail.com" && password == "password123") {
            _user.value = UiState.Loading
            viewModelScope.launch {
                userRepository.saveDummyUserLogin(email)
                    .catch {
                        _user.value = UiState.Error(it.message.toString())
                    }
                    .collect { data ->
                        try {
//                        if (!data.success) {
//                            if (data.message == "Not found" || data.message == "Bad request") {
//                                _user.value = UiState.Error("Email or Password is incorrect.\nPlease try again.")
//                                return@collect
//                            }
//                            _user.value = UiState.Error(data.message)
//                            return@collect
//                        }
                            _user.value = UiState.Success(data)
                        } catch (e: Exception) {
                            _user.value = UiState.Error(e.message.toString())
                        }
                    }
            }
        } else {
            _user.value = UiState.Error("Email or password incorrect \n Please try again")
            return
        }
    }

    fun register( email: String, password: String, confirm_password: String, displayName: String, phone: String, address: String, role: String) {
        if ( email.isEmpty() || password.isEmpty() || confirm_password.isEmpty() || displayName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            _auth.value = UiState.Error("All fields must be filled")
            return
        }
        if (password != confirm_password) {
            _auth.value = UiState.Error("Password and Confirm Password\nmust be the same")
            return
        }
        _auth.value = UiState.Loading
        viewModelScope.launch {
            userRepository.register( email = email, password = password, displayName = displayName, phone = phone, address = address, role = role)
                .catch {
                    _auth.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    try {
                        if (!data.success) {
                            _auth.value = UiState.Error(data.message)
                            return@collect
                        }
                        _auth.value = UiState.Success(data)
                    } catch (e: Exception) {
                        _auth.value = UiState.Error(e.message.toString())
                    }
                }
        }
    }


    fun verify( codeOTP: Int) {
        if (codeOTP == 0) {
            _auth.value = UiState.Error("Code OTP can't be empty")
            return
        }
        _auth.value = UiState.Loading
        viewModelScope.launch {
            userRepository.verify( codeOTP = codeOTP)
                .catch {
                    _auth.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    try {
                        if (!data.success) {
                            _auth.value = UiState.Error(data.message)
                            return@collect
                        }
                        _auth.value = UiState.Success(data)
                        userRepository.destroyVerifyUID()
                    } catch (e: Exception) {
                        _auth.value = UiState.Error(e.message.toString())
                    }
                }
        }
    }

    fun checkRegistered() {
        _verifyData.value = UiState.Loading
        viewModelScope.launch {
            val data = userRepository.getVerifyUID()
            if (data.authUID.isNotEmpty()) {
                _isRegistered.value = UiState.Success(true)
                _verifyData.value = UiState.Success(data)
            } else {
                _isRegistered.value = UiState.Success(false)
                _verifyData.value = UiState.Success(data)
            }
        }
    }

    fun destroyVerifyUID() {
        viewModelScope.launch {
            userRepository.destroyVerifyUID()
        }
    }

}