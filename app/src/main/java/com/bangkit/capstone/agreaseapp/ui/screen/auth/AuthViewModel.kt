package com.bangkit.capstone.agreaseapp.ui.screen.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel (
    private val userRepository: UserRepository
): ViewModel() {
    private val _user: MutableState<UiState<UserModel>> = mutableStateOf(UiState.Unauthorized)
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    val user: MutableState<UiState<UserModel>>
        get() = _user

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

    fun register(email: String, password: String, confirm_password: String) {
        if ( email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
            _user.value = UiState.Error("All fields must be filled")
            return
        }
        if (password != confirm_password) {
            _user.value = UiState.Error("Password and Confirm Password must be the same")
            return
        }
        _user.value = UiState.Loading
        viewModelScope.launch {
            userRepository.register(email = email, password = password)
                .catch {
                    _user.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    try {
                        if (!data.success) {
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

    fun getUser() = userRepository.getUser()
}