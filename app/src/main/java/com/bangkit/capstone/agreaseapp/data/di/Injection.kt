package com.bangkit.capstone.agreaseapp.data.di

import android.content.Context
import com.bangkit.capstone.agreaseapp.data.local.preference.UserPreference
import com.bangkit.capstone.agreaseapp.data.local.preference.dataStore
import com.bangkit.capstone.agreaseapp.data.remote.retrofit.ApiConfig
import com.bangkit.capstone.agreaseapp.data.repository.UserRepository

object Injection {
//    fun provideArticleRepository(context: Context): ArticleRepository {
//        val apiService = ApiConfig.getApiService()
//        val userPreference = UserPreference.getInstance(context.dataStore)
//        return ArticleRepository.getInstance(apiService = apiService, userPreference = userPreference)
//    }

    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(userPreference = userPreference, apiService = apiService)
    }

//    fun provideScheduleRepository(context: Context): ScheduleRepository {
//        val scheduleDatabase = ScheduleDatabase.getInstance(context)
//        val scheduleDao = scheduleDatabase.scheduleDao()
//        return ScheduleRepository.getInstance(scheduleDao)
//    }

//    fun provideReportRepository(context: Context): ReportRepository {
//        val apiService = ApiConfig.getApiService()
//        val apiServiceMl = ApiConfig.getApiServiceMl()
//        val userPreference = UserPreference.getInstance(context.dataStore)
//        return ReportRepository.getInstance(userPreference = userPreference, apiService = apiService, apiServiceMl = apiServiceMl)
//    }
}