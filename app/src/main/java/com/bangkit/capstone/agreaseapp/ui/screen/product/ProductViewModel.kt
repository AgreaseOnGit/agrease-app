package com.bangkit.capstone.agreaseapp.ui.screen.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _products: MutableStateFlow<UiState<ProductModel>> = MutableStateFlow(UiState.Loading)
    val products: StateFlow<UiState<ProductModel>>
        get() = _products

    fun getDetail(id: String) {
        _products.value = UiState.Loading
        viewModelScope.launch {
            productRepository.getProductById(id)
                .catch {
                    _products.value = UiState.Error(it.message.toString())
                }
                .collect { product ->
                    try {
                        if (!product.success) {
                            _products.value = UiState.Error(product.message)
                            return@collect
                        }
                        _products.value = UiState.Success(product.data)
                    } catch (e: Exception) {
                        _products.value = UiState.Error(e.message.toString())
                    }
                }
        }
    }
}