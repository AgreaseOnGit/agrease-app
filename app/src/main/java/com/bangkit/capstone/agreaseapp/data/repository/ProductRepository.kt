package com.bangkit.capstone.agreaseapp.data.repository

import com.bangkit.capstone.agreaseapp.data.local.preference.UserPreference
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.remote.response.TemplateResponse
import com.bangkit.capstone.agreaseapp.data.remote.retrofit.ApiService
import com.bangkit.capstone.agreaseapp.utils.processError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ProductRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    fun getProducts(): Flow<TemplateResponse<List<ProductModel>>> =
        flow {
            val productsFromApi = apiService.getProducts()
            if (!productsFromApi.isSuccessful) {
                val message = productsFromApi.processError()

                emit(TemplateResponse(success = false, message = message, data = emptyList()))
                return@flow
            }

            productsFromApi.body()?.apply {
                emit(this)
            }
        }.catch { e ->
            emit(TemplateResponse(success = false, message = e.message.toString(), data = emptyList()))
        }

    fun getProductById(id: String): Flow<TemplateResponse<ProductModel>> =
        flow {
            val articlesFromApi = apiService.getProductById(id = id)
            if (!articlesFromApi.isSuccessful) {
                val message = articlesFromApi.processError()

                emit(TemplateResponse(success = false, message = message, data = ProductModel("", "", "", "", "", "")))
                return@flow
            }

            articlesFromApi.body()?.apply {
                emit(this)
            }
        }.catch { e ->
            emit(TemplateResponse(success = false, message = e.message.toString(), data = ProductModel("", "", "", "", "", "")))
        }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(userPreference, apiService)
            }.also {
                instance = it
            }
    }
}