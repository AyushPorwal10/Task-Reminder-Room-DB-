package com.example.checkingagp

import android.content.Context
import androidx.room.Room
import com.example.checkingagp.room.AppDatabase
import com.example.checkingagp.room.TaskDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DiModules {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                .addHeader("x-rapidapi-key", "958f1b2498mshd2549ec2620595cp1efabajsnda88dd1e9bb6")
                .addHeader("x-rapidapi-host", "linkedin-api8.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://linkedin-api8.p.rapidapi.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getDAO(retrofit: Retrofit) : My_DAO {
        return retrofit.create(My_DAO::class.java)
    }

    @Provides
    @Singleton

    fun providesRoomDatabase(@ApplicationContext context : Context) : AppDatabase{
        return Room.databaseBuilder(
            context ,
            AppDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun getRoomDao(database: AppDatabase) : TaskDAO{
        return database.getDAO()
    }
}