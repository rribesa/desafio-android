package com.picpay.desafio.android.user.viewmodel.status

import com.picpay.desafio.android.user.model.User

sealed class UserStatus {
    data class UserSuccess(val users: List<User>) : UserStatus()
    data class UserError(val error: Throwable) : UserStatus()
    data class UserEmptyError(val error: Throwable) : UserStatus()
    data class UserNetworkError(val error: Throwable) : UserStatus()
    data class UserDatabaseError(val error: Throwable) : UserStatus()
}