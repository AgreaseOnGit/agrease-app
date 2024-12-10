package com.bangkit.capstone.agreaseapp.ui.screen.product.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.SellerDummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UpdateProductViewModel (
    private val userRepository: UserRepository
): ViewModel() {
    private val _user: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<UserModel>>
        get() = _user

    private val _product: MutableStateFlow<UiState<ProductModel>> = MutableStateFlow(UiState.Loading)
    val product: StateFlow<UiState<ProductModel>>
        get() = _product

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
    }

    fun getDetail( id: Int) {
        _product.value = UiState.Success(SellerDummyDataSource.dummyProducts[id])
//        _product.value = UiState.Loading
//        viewModelScope.launch {
//            productRepository.getProductById(id)
//                .catch {
//                    _product.value = UiState.Error(it.message.toString())
//                }
//                .collect { product ->
//                    try {
//                        if (!product.success) {
//                            _product.value = UiState.Error(product.message)
//                            return@collect
//                        }
//                        _product.value = UiState.Success(product.data)
//                    } catch (e: Exception) {
//                        _product.value = UiState.Error(e.message.toString())
//                    }
//                }
//        }
    }
}