package com.bangkit.capstone.agreaseapp.ui.screen.product

import androidx.lifecycle.ViewModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _product: MutableStateFlow<UiState<ProductModel>> = MutableStateFlow(UiState.Loading)
    val product: StateFlow<UiState<ProductModel>>
        get() = _product

    fun getDetail(id: Int) {
        _product.value = UiState.Success(DummyDataSource.dummyProducts[id])
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