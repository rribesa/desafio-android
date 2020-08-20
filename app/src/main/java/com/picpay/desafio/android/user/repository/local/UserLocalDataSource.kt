package com.picpay.desafio.android.user.repository.local

import com.picpay.desafio.android.user.repository.local.database.entity.UserEntity

interface UserLocalDataSource {

    suspend fun getAllUsers(): List<UserEntity>
    suspend fun deleteAndInsert(users: List<UserEntity>)
}