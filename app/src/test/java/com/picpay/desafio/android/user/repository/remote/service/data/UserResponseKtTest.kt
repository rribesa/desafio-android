package com.picpay.desafio.android.user.repository.remote.service.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class UserResponseKtTest {

    @Test
    fun mapResponseEntity() {
        val listResponse: List<UserResponse> = emptyList()
        runBlocking {
            val entiy = listResponse.mapResponseEntity()
            assertNotNull(entiy)
        }
    }
}