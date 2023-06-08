package com.example.framewiz.data.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}