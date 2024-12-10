package com.bangkit.capstone.agreaseapp.ui.screen.transaction.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.TransactionModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.model.dummy.SellerDummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailTransactionViewModel(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _transaction: MutableStateFlow<UiState<TransactionModel>> = MutableStateFlow(
        UiState.Loading)
    val transaction: StateFlow<UiState<TransactionModel>>
        get() = _transaction

    private val _user: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<UserModel>>
        get() = _user

    private val _products: MutableStateFlow<UiState<List<ProductModel>>> = MutableStateFlow(UiState.Loading)
    val products: StateFlow<UiState<List<ProductModel>>>
        get() = _products

    private val _userRole: MutableState<UiState<String>> = mutableStateOf(UiState.Loading)
    val userRole: MutableState<UiState<String>>
        get() = _userRole

    fun getUserRole() {
        viewModelScope.launch {
            _userRole.value = UiState.Success(userRepository.getRole())
        }
    }

    fun getDetailTransactions(role: String, id: Int) {
        if (role == "buyer"){
            _transaction.value = UiState.Success(DummyDataSource.dummyTransaction[id])
        } else {
            _transaction.value = UiState.Success(SellerDummyDataSource.dummyTransaction[id])
        }
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
}