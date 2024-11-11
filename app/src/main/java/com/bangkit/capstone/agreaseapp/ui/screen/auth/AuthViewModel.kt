package com.bangkit.capstone.agreaseapp.ui.screen.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.UserModel
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

    fun login(email: String, password: String) {
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

    fun register(name: String, email: String, password: String, confirm_password: String) {
        _user.value = UiState.Loading
        viewModelScope.launch {
            userRepository.register(name = name, email = email, password = password, confirm_password = confirm_password)
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