package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.CategoryModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.TransactionModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _transactions: MutableStateFlow<UiState<List<TransactionModel>>> = MutableStateFlow(UiState.Loading)
    val transactions: StateFlow<UiState<List<TransactionModel>>>
        get() = _transactions
    fun getTransactions() {
        _transactions.value = UiState.Success(DummyDataSource.dummyTransaction)

    }
}