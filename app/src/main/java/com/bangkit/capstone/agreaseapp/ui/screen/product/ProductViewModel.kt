package com.bangkit.capstone.agreaseapp.ui.screen.product

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.model.dummy.SellerDummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _product: MutableStateFlow<UiState<ProductModel>> = MutableStateFlow(UiState.Loading)
    val product: StateFlow<UiState<ProductModel>>
        get() = _product

    private val _userRole: MutableState<UiState<String>> = mutableStateOf(UiState.Loading)
    val userRole: MutableState<UiState<String>>
        get() = _userRole

    fun getUserRole() {
        viewModelScope.launch {
            _userRole.value = UiState.Success(userRepository.getRole())
        }
    }

    fun getDetail(role: String, id: Int) {
        if(role == "buyer"){
            _product.value = UiState.Success(DummyDataSource.dummyProducts[id])
        } else {
            _product.value = UiState.Success(SellerDummyDataSource.dummyProducts[id])
        }
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