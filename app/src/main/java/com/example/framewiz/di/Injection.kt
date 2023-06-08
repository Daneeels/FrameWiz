package com.example.framewiz.di

import android.content.Context
import com.example.framewiz.data.FrameRepository
import com.example.framewiz.data.UserPreference
import com.example.framewiz.data.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): FrameRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference(context)
        return FrameRepository(apiService, pref)
    }
}