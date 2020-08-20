package com.picpay.desafio.android.user.repository.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.user.repository.local.UserLocalDatalSource
import com.picpay.desafio.android.user.repository.local.database.constants.DataBaseConstants
import com.picpay.desafio.android.user.repository.local.database.dao.UserDAO
import com.picpay.desafio.android.user.repository.local.database.entity.UserEntity

@Database(
    version = DataBaseConstants.DATABASE_VERSION,
    entities = [UserEntity::class],
    exportSchema = false
)
abstract class UserDataBase : UserLocalDatalSource, RoomDatabase() {
    abstract fun userDAO(): UserDAO
}