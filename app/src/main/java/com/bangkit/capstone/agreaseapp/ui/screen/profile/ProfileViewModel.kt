package com.bangkit.capstone.agreaseapp.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val userRepository: UserRepository,
): ViewModel() {
    private val _user: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<UserModel>>
        get() = _user

    fun getUser() {

       // _user.value = UiState.Success(UserModel(1, "Username", "Buyer","test@example.com", "https://www.its.ac.id/international/wp-content/uploads/sites/66/2020/02/blank-profile-picture-973460_1280-1.jpg", ""))
//        viewModelScope.launch {
//            if (_user.value is UiState.Success) {
//                return@launch
//            }
//            userRepository.getUserPreference()
//                .catch {
//                    _user.value = UiState.Error(it.message.toString())
//                }
//                .collect { user ->
//                    try {
//                        _user.value = UiState.Success(user)
//                    } catch (e: Exception) {
//                        _user.value = UiState.Error(e.message.toString())
//                    }
//                }
//        }

      _user.value = UiState.Success(UserModel("1", "Sample User","test@example.com", "https://www.its.ac.id/international/wp-content/uploads/sites/66/2020/02/blank-profile-picture-973460_1280-1.jpg", ""))
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
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logOut()
        }
    }
}