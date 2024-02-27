package com.example.courseapp.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.courseapp.common.AppDataStoreManager
import com.example.courseapp.common.Constants
import com.example.courseapp.common.Constants.BASE_URL
import com.example.courseapp.authentication.data.remote.AuthApi
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.data.repository.AuthRepository
import com.example.courseapp.domain.repository.AuthRepositoryImp
import com.example.courseapp.teacher_features.data.remote.TeacherApi
import com.example.courseapp.teacher_features.data.remote.UpdatePartsApi
import com.example.courseapp.teacher_features.data.repository.TeacherRepository
import com.example.courseapp.teacher_features.domain.repository.TeacherRepositoryImp
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleNetwork {


    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideTokenFlow(dataStore: AppDataStoreManager): Flow<String?> {
        return dataStore.currentToken
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenFlow: Flow<String?>): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = runBlocking {
                    tokenFlow.firstOrNull() // Fetch the token asynchronously
                }
                Log.d("AppModule", "provideOkHttpClient: $token")

                val request = chain.request().newBuilder()
                    .apply {
                        token?.let {
                            addHeader("Authorization", "Bearer $it")
                            addHeader("Accept", "application/json")
                            addHeader("Content-Type", "application/json")
                        }
                    }
                    .build()
                chain.proceed(request)
            }
            .build()

        return client
    }


    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTeacherApi(okHttpClient: OkHttpClient): TeacherApi {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TeacherApi::class.java)
    }



    @Provides
    @Singleton
    fun provideKtorClient(
        tokenFlow: Flow<String?>
    ):HttpClient
    {
        val token = runBlocking {
            tokenFlow.firstOrNull() // Fetch the token asynchronously
        }

        return HttpClient {
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
            install(Logging) {
                logger = object : Logger{
                    override fun log(message: String) {
                        Log.d("KtorFitLogger","log: $message")
                    }

                }
                level = LogLevel.ALL
            }
            token?.let {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $it")
                }
            }

        }
    }

    @Provides
    @Singleton
    fun provideUpdateApi(httpClient: HttpClient): UpdatePartsApi {

        return Ktorfit.Builder()
            .baseUrl("$BASE_URL/")
            .httpClient(httpClient)
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        tokenFlow: Flow<String?>
    ): AuthRepository {
        return AuthRepositoryImp(api, tokenFlow)
    }

    @Provides
    @Singleton
    fun provideTeacherRepository(api: TeacherApi, tokenFlow: Flow<String?>, updatePartsApi: UpdatePartsApi): TeacherRepository {
        return TeacherRepositoryImp(teacherApi = api, tokenFlow = tokenFlow, updatePartsApi =updatePartsApi )
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(Constants.DATA_STORE_NAME)
        }


}