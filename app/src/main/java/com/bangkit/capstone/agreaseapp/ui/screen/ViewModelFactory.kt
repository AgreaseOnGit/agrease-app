package com.bangkit.capstone.agreaseapp.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.agreaseapp.AppViewModel
import com.bangkit.capstone.agreaseapp.data.di.Injection
import com.bangkit.capstone.agreaseapp.data.repository.ProductRepository
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository
import com.bangkit.capstone.agreaseapp.ui.screen.auth.AuthViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.category.CategoryViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.checkout.CheckoutViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.home.HomeViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.product.ProductViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.profile.ProfileViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.profile.account.MyAccountViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.search.SearchViewModel
import com.bangkit.capstone.agreaseapp.ui.screen.transaction.TransactionViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    private val viewModelMap = mapOf(
        HomeViewModel::class.java to {
            HomeViewModel(
                userRepository,
                productRepository
            )
        },
        AuthViewModel::class.java to { AuthViewModel(userRepository) },
        AppViewModel::class.java to { AppViewModel(userRepository) },
        ProfileViewModel::class.java to { ProfileViewModel(userRepository) },
        MyAccountViewModel::class.java to { MyAccountViewModel(userRepository) },
        TransactionViewModel::class.java to { TransactionViewModel(productRepository) },
        CategoryViewModel::class.java to { CategoryViewModel(productRepository) },
        ProductViewModel::class.java to { ProductViewModel(productRepository) },
        CheckoutViewModel::class.java to { CheckoutViewModel(productRepository, userRepository) },
        SearchViewModel::class.java to { SearchViewModel(productRepository) },
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        viewModelMap[modelClass]?.let {
            return it.invoke() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.provideProductRepository(context)
                )
            }.also { INSTANCE = it }
    }
}