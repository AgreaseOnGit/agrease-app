package com.bangkit.capstone.agreaseapp.ui.screen.product

import androidx.lifecycle.ViewModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _products: MutableStateFlow<UiState<ProductModel>> = MutableStateFlow(UiState.Loading)
    val products: StateFlow<UiState<ProductModel>>
        get() = _products

    fun getDetail(id: Int) {
        _products.value = UiState.Success(DummyDataSource.dummyProducts[id-1])
    }
}