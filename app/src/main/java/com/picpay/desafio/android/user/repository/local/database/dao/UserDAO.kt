package com.picpay.desafio.android.user.repository.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.picpay.desafio.android.user.repository.local.UserLocalDataSource
import com.picpay.desafio.android.user.repository.local.database.entity.UserEntity

@Dao
interface UserDAO : UserLocalDataSource {

    @Query("SELECT * FROM User")
    override suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()

    @Transaction
    override suspend fun deleteAndInsert(users: List<UserEntity>) {
        deleteAllUsers()
        insertAllUsers(users)
    }
}