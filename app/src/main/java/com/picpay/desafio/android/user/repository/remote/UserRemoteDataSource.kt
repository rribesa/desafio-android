package com.picpay.desafio.android.user.repository.remote

import com.picpay.desafio.android.user.repository.remote.service.data.UserResponse

interface UserRemoteDataSource {
    suspend fun getUsers(): List<UserResponse>
}