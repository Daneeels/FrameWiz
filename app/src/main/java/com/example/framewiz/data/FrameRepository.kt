package com.example.framewiz.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.framewiz.data.api.ApiService
import com.example.framewiz.data.api.LoginResponse
import com.example.framewiz.data.api.RegisterResponse

class FrameRepository(
    private val apiService: ApiService,
    private val pref: UserPreference,
) {

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Login: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        email: String,
        nama: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(email, nama, password)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.e("RegisterViewModel", "Register: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
}