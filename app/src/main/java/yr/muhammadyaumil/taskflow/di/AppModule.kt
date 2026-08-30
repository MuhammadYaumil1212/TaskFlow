package yr.muhammadyaumil.taskflow.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepository
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        repositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}