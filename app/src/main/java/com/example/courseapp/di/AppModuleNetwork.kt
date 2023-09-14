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
import com.example.courseapp.data.remote.CourseApi
import com.example.courseapp.data.remote.UpdateApi
import com.example.courseapp.domain.repository.AuthRepositoryImp
import com.example.courseapp.domain.repository.TeacherRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideKtorClient(
        tokenFlow: Flow<String?>
    ):HttpClient
    {
        val token = runBlocking {
            tokenFlow.firstOrNull() // Fetch the token asynchr onously
        }

        return HttpClient {
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            token?.let {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            defaultRequest {

            }

        }
    }

    @Provides
    @Singleton
    fun provideUpdateApi(httpClient: HttpClient): UpdateApi {

        return Ktorfit.Builder()
            .baseUrl(BASE_URL)
            .httpClient(httpClient)
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(tokenFlow: Flow<String?>): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor { chain ->
                val token = runBlocking {
                    tokenFlow.firstOrNull() // Fetch the token asynchr onously
                }
                Log.d("AppModule", "provideOkHttpClient: $token")

                val request = chain.request().newBuilder()
                    .apply {
                        token?.let {
                            addHeader("Authorization", "Bearer $it")
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
    fun provideCourseApi(okHttpClient: OkHttpClient): CourseApi {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTeacherRepository(api: CourseApi,context: Context,tokenFlow: Flow<String?>,updateApi: UpdateApi): TeacherRepositoryImp {
        return TeacherRepositoryImp(courseApi = api, tokenFlow = tokenFlow, updateApi =updateApi )
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        api: CourseApi,
        tokenFlow: Flow<String?>
    ): AuthRepositoryImp {
        Log.d("AppModule", "provideLogoutUserRepository: ")
        return AuthRepositoryImp(api, tokenFlow)
    }




    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(Constants.DATA_STORE_NAME)
        }


}