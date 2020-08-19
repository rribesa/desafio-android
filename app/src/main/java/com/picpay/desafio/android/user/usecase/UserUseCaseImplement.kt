package com.picpay.desafio.android.user.usecase

import com.picpay.desafio.android.user.model.User
import com.picpay.desafio.android.user.repository.UserRepository
import com.picpay.desafio.android.user.repository.remote.service.exception.UserServiceException
import com.picpay.desafio.android.user.usecase.exception.UserEmptyException

class UserUseCaseImplement(private val repository: UserRepository) : UserUseCase {
    override suspend fun getUsers(): Result<List<User>> {
        return try {
            getUserResultNotEmptyOrError(repository.getUsers())
        } catch (exception: UserServiceException) {
            getUserResultNotEmptyOrError(repository.getUsersCache())
        }
    }

    private fun getUserResultNotEmptyOrError(result: Result<List<User>>): Result<List<User>> {
        return if (result.isSuccess && result.getOrNull().isNullOrEmpty()) {
            Result.failure(UserEmptyException())
        } else {
            result
        }
    }
}