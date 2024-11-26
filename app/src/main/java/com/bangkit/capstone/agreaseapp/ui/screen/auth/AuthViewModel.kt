package com.bangkit.capstone.agreaseapp.ui.screen.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.remote.response.RegisterResponse
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
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

    fun login(email: String, password: String) {
        if(email.isEmpty() || password.isEmpty()){
            _user.value = UiState.Error("Email or password can't be empty")
            return
        }
        _user.value = UiState.Loading
        viewModelScope.launch {
            userRepository.login(email = email, password = password)
                .catch {
                    _user.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    try {
                        if (!data.success) {
                            if (data.message == "Unauthorized") {
                                _user.value = UiState.Error("Email or Password is incorrect. Please try again.")
                                return@collect
                            }
                            _user.value = UiState.Error(data.message)
                            return@collect
                        }
                        _user.value = UiState.Success(data.data)
                    } catch (e: Exception) {
                        _user.value = UiState.Error(e.message.toString())
                    }
                }
        }
    }

    fun register( email: String, password: String, confirm_password: String, displayName: String, phone: String, address: String, role: String) {
        if ( email.isEmpty() || password.isEmpty() || confirm_password.isEmpty() || displayName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            _auth.value = UiState.Error("All fields must be filled")
            return
        }
        if (password != confirm_password) {
            _auth.value = UiState.Error("Password and Confirm Password must be the same")
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
        if (codeOTP.toString().isEmpty()) {
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
                    } catch (e: Exception) {
                        _auth.value = UiState.Error(e.message.toString())
                    }
                }
        }
    }

    fun checkRegistered() {
        viewModelScope.launch {
            _isRegistered.value = UiState.Success(userRepository.getRegisteredUID().isNotEmpty())
        }
    }

}