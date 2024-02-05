package newjeans.bunnies.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import newjeans.bunnies.network.auth.AuthApiRepositoryImpl
import newjeans.bunnies.network.auth.AuthRepository
import newjeans.bunnies.network.post.PostApiRepositoryImpl
import newjeans.bunnies.network.post.PostRepository
import newjeans.bunnies.network.user.UserApiRepositoryImpl
import newjeans.bunnies.network.user.UserRepository

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authApiRepositoryImpl: AuthApiRepositoryImpl): AuthRepository =
        authApiRepositoryImpl

    @Provides
    @Singleton
    fun providePostRepository(postApiRepositoryImpl: PostApiRepositoryImpl): PostRepository =
        postApiRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userApiRepositoryImpl: UserApiRepositoryImpl): UserRepository =
        userApiRepositoryImpl

}