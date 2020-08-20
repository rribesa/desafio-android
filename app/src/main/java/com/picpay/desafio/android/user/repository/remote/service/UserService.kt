package com.picpay.desafio.android.user.repository.remote.service

import com.picpay.desafio.android.user.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.user.repository.remote.service.data.UserResponse
import retrofit2.http.GET

interface UserService : UserRemoteDataSource {
    @GET("users")
    override suspend fun getUsers(): List<UserResponse>
}