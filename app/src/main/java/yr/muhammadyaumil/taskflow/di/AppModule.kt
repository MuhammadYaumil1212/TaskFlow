package yr.muhammadyaumil.taskflow.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yr.muhammadyaumil.taskflow.data.signIn.Repository.SignInRepository
import yr.muhammadyaumil.taskflow.data.signIn.Repository.SignInRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindSignInRepository(
        repositoryImpl: SignInRepositoryImpl
    ): SignInRepository
}