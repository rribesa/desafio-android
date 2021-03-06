package com.picpay.desafio.android.user.di

import androidx.room.Room
import br.com.ribeiro.network.WebClient
import com.picpay.desafio.android.user.repository.UserRepository
import com.picpay.desafio.android.user.repository.UserRepositoryImplement
import com.picpay.desafio.android.user.repository.local.UserLocalDataSource
import com.picpay.desafio.android.user.repository.local.database.UserDataBase
import com.picpay.desafio.android.user.repository.local.database.constants.DataBaseConstants
import com.picpay.desafio.android.user.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.user.repository.remote.service.UserService
import com.picpay.desafio.android.user.repository.remote.service.constants.ServiceConstant
import com.picpay.desafio.android.user.usecase.UserUseCase
import com.picpay.desafio.android.user.usecase.UserUseCaseImplement
import com.picpay.desafio.android.user.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object UserDI {
    private val viewModelModule = module {
        viewModel { UserViewModel(useCase = get()) }
        factory { UserUseCaseImplement(repository = get()) as UserUseCase }
        factory {
            UserRepositoryImplement(
                remoteDataSource = get(),
                localDataSource = get()
            ) as UserRepository
        }
        single { WebClient.service<UserService>(ServiceConstant.URL) as UserRemoteDataSource }
        single {
            Room.databaseBuilder(
                get(),
                UserDataBase::class.java,
                DataBaseConstants.DATABASE_NAME
            )
                .build()
        }
        single { get<UserDataBase>().userDAO() as UserLocalDataSource }
    }

    fun init() = loadKoinModules(viewModelModule)
}