package com.example.framewiz.data.api

import androidx.annotation.RawRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") Email: String,
        @Field("password") Password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") Email: String,
        @Field("nama") Name: String,
        @Field("password") Password: String
    ): RegisterResponse

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part image: MultipartBody.Part
    ): PredictResponse
}
