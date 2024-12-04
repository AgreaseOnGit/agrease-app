package com.bangkit.capstone.agreaseapp.ui.screen.category

import androidx.lifecycle.ViewModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _products: MutableStateFlow<UiState<List<ProductModel>>> = MutableStateFlow(UiState.Loading)
    val products: StateFlow<UiState<List<ProductModel>>>
        get() = _products

    fun getProducts(category: String) {
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
}