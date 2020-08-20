package com.picpay.desafio.android.user.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.user.CoroutineTestRule
import com.picpay.desafio.android.user.mock.UserMock
import com.picpay.desafio.android.user.repository.local.database.exception.UserDatabaseException
import com.picpay.desafio.android.user.repository.remote.service.exception.UserServiceException
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
    fun `ao consultar e o useCase estiver ok deve retornar UserStatus Success`() {
        val useCase: UserUseCase = mockk()
        coEvery { useCase.getUsers() } returns UserMock.mockUserSuccess()
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.fetch()
            assertEquals(UserStatus.UserSuccess(UserMock.mockUserEntity()), viewModel.state.value)
        }
    }

    @Test
    fun `ao consultar e o useCase estiver vazia deve retornar UserStatus falha`() {
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
    fun `ao consultar e o useCase estiver erro de network deve retornar UserStatus falha`() {
        val useCase: UserUseCase = mockk()
        val exception =
            UserServiceException(IOException("mock"))
        coEvery { useCase.getUsers() } returns UserMock.mockUserFailure(exception)
        val viewModel = UserViewModel(useCase)
        runBlocking {
            viewModel.fetch()
            assertEquals(UserStatus.UserNetworkError(exception), viewModel.state.value)
        }
    }

    @Test
    fun `ao consultar e o useCase estiver erro de database deve retornar UserStatus falha`() {
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
    fun `ao consultar e o useCase estiver erro nao tratado deve retornar UserStatus falha`() {
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