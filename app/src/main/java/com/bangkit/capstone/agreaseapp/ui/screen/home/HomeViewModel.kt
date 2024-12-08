package com.bangkit.capstone.agreaseapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.CategoryModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
): ViewModel() {
    private val _categories: MutableStateFlow<UiState<List<CategoryModel>>> = MutableStateFlow(UiState.Loading)
    val categories: StateFlow<UiState<List<CategoryModel>>>
        get() = _categories

    private val _products: MutableStateFlow<UiState<List<ProductModel>>> = MutableStateFlow(UiState.Loading)
    val products: StateFlow<UiState<List<ProductModel>>>
        get() = _products

    private val _user: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<UserModel>>
        get() = _user

    fun getcategories() {
        _categories.value = UiState.Success(DummyDataSource.dummyCatgories)
    }

    fun getProducts() {
        _products.value = UiState.Success(DummyDataSource.dummyProducts)
//        _products.value = UiState.Loading
//        viewModelScope.launch {
//            if (_products.value is UiState.Success) {
//                return@launch
//            }
//            productRepository.getProducts()
//                .catch {
//                    _products.value = UiState.Error(it.message.toString())
//                }
//                .collect { products ->
//                    try {
//                        if (!products.success) {
//                            _products.value = UiState.Error(products.message)
//                            return@collect
//                        }
//                        _products.value = UiState.Success(products.data)
//                    } catch (e: Exception) {
//                        _products.value = UiState.Error(e.message.toString())
//                    }
//                }
//        }
    }

    fun getUser() {
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
}