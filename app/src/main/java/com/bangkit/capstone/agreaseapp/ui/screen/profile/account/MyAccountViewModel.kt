package com.bangkit.capstone.agreaseapp.ui.screen.profile.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MyAccountViewModel (
    private val userRepository: UserRepository
): ViewModel() {
    private val _user: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<UserModel>>
        get() = _user

    fun getUser() {
        _user.value = UiState.Loading
        viewModelScope.launch {
            if (_user.value is UiState.Success) {
                return@launch
            }
            userRepository.getUserPreference()
                .catch {
                    _user.value = UiState.Error(it.message.toString())
                }
                .collect { user ->
                    try {
                        _user.value = UiState.Success(user)
                    } catch (e: Exception) {
                        _user.value = UiState.Error(e.message.toString())
                    }
                }
        }
//        viewModelScope.launch {
//            userRepository.getUser()
//                .catch {
//                    _user.value = UiState.Error(it.message.toString())
//                }
//                .collect { data ->
//                    try {
//                        if (!data.success) {
//                            if (data.message == "Unauthorized") {
//                                _user.value = UiState.Unauthorized
//                                return@collect
//                            }
//                            _user.value = UiState.Error(data.message)
//                            return@collect
//                        }
//                        _user.value = UiState.Success(data.data)
//                    } catch (e: Exception) {
//                        _user.value = UiState.Error(e.message.toString())
//                    }
//                }
//        }
    }

//    fun updateUser(name: String, profile: File?, email: String) {
//        _user.value = UiState.Loading
//        viewModelScope.launch {
//            userRepository.updateUser(name = name, profile = profile, email = email)
//                .catch {
//                    _user.value = UiState.Error(it.message.toString())
//                }
//                .collect { data ->
//                    try {
//                        if (!data.success) {
//                            _user.value = UiState.Error(data.message)
//                            return@collect
//                        }
//                        _user.value = UiState.Success(data.data)
//                    } catch (e: Exception) {
//                        _user.value = UiState.Error(e.message.toString())
//                    }
//                }
//        }
//    }
}