package com.example.checkingagp

import retrofit2.http.GET
import retrofit2.http.Query


interface My_DAO  {
    @GET(".")
    suspend fun getUserDataFromUserName(
        @Query("username") username :String
    ): MainUserProfileData
}