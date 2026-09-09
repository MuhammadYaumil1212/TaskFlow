package yr.muhammadyaumil.taskflow.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import yr.muhammadyaumil.taskflow.data.addTracker.dataSources.HabitRemoteDataSource
import yr.muhammadyaumil.taskflow.data.addTracker.dataSources.HabitRemoteDataSourceImpl
import yr.muhammadyaumil.taskflow.data.addTracker.repository.HabitRepository
import yr.muhammadyaumil.taskflow.data.addTracker.repository.HabitRepositoryImpl
import yr.muhammadyaumil.taskflow.data.authentication.dataSources.AuthRemote
import yr.muhammadyaumil.taskflow.data.authentication.dataSources.AuthRemoteImpl
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepository
import yr.muhammadyaumil.taskflow.data.authentication.repository.AuthenticationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideHabitRemoteDataSource(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): HabitRemoteDataSource {
        return HabitRemoteDataSourceImpl(firestore, auth)
    }

    @Provides
    @Singleton
    fun provideHabitRepository(
        remoteDataSource: HabitRemoteDataSource
    ): HabitRepository {
        return HabitRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        @ApplicationContext context: Context
    ): AuthRemote {
        return AuthRemoteImpl(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemote: AuthRemote
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authRemote)
    }
}