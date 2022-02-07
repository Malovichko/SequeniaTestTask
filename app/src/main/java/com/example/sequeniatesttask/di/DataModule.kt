package com.example.sequeniatesttask.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.sequeniatesttask.data.RepositoryImpl
import com.example.sequeniatesttask.data.network.FilmsApiService
import com.example.sequeniatesttask.data.preferences.SharedPref
import com.example.sequeniatesttask.domain.repository.Repository
import com.example.sequeniatesttask.presentation.fragmentFilms.FilmsPresenter
import com.example.sequeniatesttask.presentation.fragmentFilms.FilmsView
import com.example.sequeniatesttask.utils.PREF_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideRepository(filmsApiService: FilmsApiService, sharedPref: SharedPref): Repository {
        return RepositoryImpl(filmsApiService, sharedPref)
    }


    @Provides
    fun providesBaseUrl(): String = "https://s3-eu-west-1.amazonaws.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()

    }

    @Provides
    @Singleton
    fun provideFilmsService(retrofit: Retrofit): FilmsApiService =
        retrofit.create(FilmsApiService::class.java)


    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE))
    }
}

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
    @Provides
    fun providePresenter(
        fragment: Fragment,
        repository: Repository
    ): FilmsPresenter {
        return FilmsPresenter(fragment as FilmsView, repository)
    }
}