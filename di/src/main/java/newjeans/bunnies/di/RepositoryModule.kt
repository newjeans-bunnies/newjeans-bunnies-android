package newjeans.bunnies.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import newjeans.bunnies.network.auth.ApiRepositoryImpl
import newjeans.bunnies.network.auth.AuthRepository

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(apiRepositoryImpl: ApiRepositoryImpl): AuthRepository =
        apiRepositoryImpl

}