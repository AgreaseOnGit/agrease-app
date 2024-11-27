package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _transactions: MutableStateFlow<UiState<List<ProductModel>>> = MutableStateFlow(UiState.Loading)
    val transactions: StateFlow<UiState<List<ProductModel>>>
        get() = _transactions
    fun getTransactions() {
        _transactions.value = UiState.Loading
        viewModelScope.launch {
            if (_transactions.value is UiState.Success) {
                return@launch
            }
            productRepository.getProducts()
                .catch {
                    _transactions.value = UiState.Error(it.message.toString())
                }
                .collect { product ->
                    try {
                        if (!product.success) {
                            _transactions.value = UiState.Error(product.message)
                            return@collect
                        }
                        _transactions.value = UiState.Success(product.data)
                    } catch (e: Exception) {
                        _transactions.value = UiState.Error(e.message.toString())
                    }
                }
        }
    }
}