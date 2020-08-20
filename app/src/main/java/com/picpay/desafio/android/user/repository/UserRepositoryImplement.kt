package com.picpay.desafio.android.user.repository

import com.picpay.desafio.android.user.model.User
import com.picpay.desafio.android.user.repository.local.UserLocalDataSource
import com.picpay.desafio.android.user.repository.local.database.entity.UserEntity
import com.picpay.desafio.android.user.repository.local.database.exception.UserDatabaseException
import com.picpay.desafio.android.user.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.user.repository.remote.service.data.UserResponse
import com.picpay.desafio.android.user.repository.remote.service.data.mapResponseEntity
import com.picpay.desafio.android.user.repository.remote.service.exception.UserServiceException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImplement(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        insertUserCache(getUserRemote().mapResponseEntity())
        return getUsersLocal()
    }

    override suspend fun getUsersCache(): Result<List<User>> {
        return getUsersLocal()
    }

    private suspend fun getUserRemote() = withContext(Dispatchers.IO) {
        try {
            remoteDataSource.getUsers()
        } catch (exception: Exception) {
            throw UserServiceException(
                exception
            )
        }
    }

    private suspend fun getUsersLocal(): Result<List<User>> = withContext(Dispatchers.IO) {
        try {
            Result.success(localDataSource.getAllUsers())
        } catch (exception: Exception) {
            Result.failure<List<UserResponse>>(
                UserDatabaseException(
                    exception
                )
            )
        }
    }

    private suspend fun insertUserCache(users: List<UserEntity>) = withContext(Dispatchers.IO) {
        localDataSource.deleteAndInsert(users)
    }
}