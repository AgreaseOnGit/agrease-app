package com.bangkit.capstone.agreaseapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.launch

class AppViewModel (
    private val userRepository: UserRepository
): ViewModel() {
    private val _isVerified: MutableState<UiState<Boolean>> = mutableStateOf(UiState.Loading)
    val isVerified: MutableState<UiState<Boolean>>
        get() = _isVerified

    fun checkVerified() {
        viewModelScope.launch {
            _isVerified.value = UiState.Success(userRepository.getVerified())
        }
    }
}