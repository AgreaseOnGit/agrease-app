package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import androidx.lifecycle.ViewModel
import com.bangkit.capstone.agreaseapp.data.model.TransactionModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionViewModel(
    private val productRepository: ProductRepository,
): ViewModel() {

    private val _transactions: MutableStateFlow<UiState<List<TransactionModel>>> = MutableStateFlow(UiState.Loading)
    val transactions: StateFlow<UiState<List<TransactionModel>>>
        get() = _transactions

    fun getTransactions() {
        _transactions.value = UiState.Success(DummyDataSource.dummyTransaction)
    }
}