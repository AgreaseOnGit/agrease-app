package com.bangkit.capstone.agreaseapp.ui.screen.transaction

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.TransactionModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.model.dummy.SellerDummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _transactions: MutableStateFlow<UiState<List<TransactionModel>>> = MutableStateFlow(UiState.Loading)
    val transactions: StateFlow<UiState<List<TransactionModel>>>
        get() = _transactions

    private val _userRole: MutableState<UiState<String>> = mutableStateOf(UiState.Loading)
    val userRole: MutableState<UiState<String>>
        get() = _userRole

    fun getUserRole() {
        viewModelScope.launch {
            _userRole.value = UiState.Success(userRepository.getRole())
        }
    }

    fun getTransactions(role: String) {
        if (role == "buyer"){
            _transactions.value = UiState.Success(DummyDataSource.dummyTransaction)
        } else {
            _transactions.value = UiState.Success(SellerDummyDataSource.dummyTransaction)
        }
    }
}