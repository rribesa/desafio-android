package com.picpay.desafio.android.user.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.user.CoroutineTestRule
import com.picpay.desafio.android.user.mock.UserMock
import com.picpay.desafio.android.user.repository.local.database.exception.UserDatabaseException
import com.picpay.desafio.android.user.usecase.UserUseCase
import com.picpay.desafio.android.user.usecase.exception.UserEmptyException
import com.picpay.desafio.android.user.viewmodel.status.UserStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class UserViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val scope = CoroutineTestRule()

    @Test
    fun `deve ter UserStatusSucces ao executar o fetch quando a usecase getusers estiver ok e a lista nao for vazia`() {
        val useCase: UserUseCase = mockk()
        coEvery { useCase.getUsers() } returns UserMock.mockUserSuccess()
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.fetch()
            assertEquals(UserStatus.UserSuccess(UserMock.mockUserEntity()), viewModel.state.value)
        }
    }

    @Test
    fun `deve ter UserStatusEmptyError ao executar o fetch quando a usecase getusers estiver ok e a lista for vazia`() {
        val useCase: UserUseCase = mockk()
        val exception =
            UserEmptyException()
        coEvery { useCase.getUsers() } returns UserMock.mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.fetch()
            assertEquals(UserStatus.UserEmptyError(exception), viewModel.state.value)
        }
    }

    @Test
    fun `deve ter UserDatabaseError ao executar o fetch quando a usecase getusers estiver com alguma exception que foi tratada`() {
        val useCase: UserUseCase = mockk()
        val exception = UserDatabaseException(IOException("mock"))
        coEvery { useCase.getUsers() } returns UserMock.mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.fetch()
            assertEquals(UserStatus.UserDatabaseError(exception), viewModel.state.value)
        }
    }

    @Test
    fun `deve ter UserError ao executar o fetch quando a usecase getusers estiver com alguma exception desconhecida`() {
        val useCase: UserUseCase = mockk()
        val exception = NullPointerException("mock")
        coEvery { useCase.getUsers() } returns UserMock.mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.fetch()
            assertEquals(UserStatus.UserError(exception), viewModel.state.value)
        }
    }
}