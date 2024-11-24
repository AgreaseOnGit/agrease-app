package com.bangkit.capstone.agreaseapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.agreaseapp.data.model.CategoryModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import com.bangkit.capstone.agreaseapp.data.model.dummy.DummyDataSource
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(
    private val userRepository: UserRepository,
): ViewModel() {
    private val _categories: MutableStateFlow<UiState<List<CategoryModel>>> = MutableStateFlow(UiState.Loading)
    val categories: StateFlow<UiState<List<CategoryModel>>>
        get() = _categories

    private val _products: MutableStateFlow<UiState<List<ProductModel>>> = MutableStateFlow(UiState.Loading)
    val products: StateFlow<UiState<List<ProductModel>>>
        get() = _products

    private val _user: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Loading)
    val user: StateFlow<UiState<UserModel>>
        get() = _user

    fun getcategories() {
        _categories.value = UiState.Success(DummyDataSource.dummyCatgories)
    }

    fun getProducts(page: Int) {
        _products.value = UiState.Success(DummyDataSource.dummyProducts)

//        viewModelScope.launch {
//            if (_products.value is UiState.Success) {
//                return@launch
//            }
//            articleRepository.getArticles(page = page)
//                .catch {
//                    _products.value = UiState.Error(it.message.toString())
//                }
//                .collect { articles ->
//                    try {
//                        if (!articles.success) {
//                            if (articles.message == "Unauthorized") {
//                                _products.value = UiState.Unauthorized
//                                return@collect
//                            }
//                            _products.value = UiState.Error(articles.message)
//                            return@collect
//                        }
//                        _products.value = UiState.Success(articles.data.take(3))
//                    } catch (e: Exception) {
//                        _products.value = UiState.Error(e.message.toString())
//                    }
//                }
//        }
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
}