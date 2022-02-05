package com.example.sequeniatesttask.data.network

import com.example.sequeniatesttask.data.network.model.SimpleListFilmsApiModel
import retrofit2.Call
import retrofit2.http.GET

interface FilmsApiService {
    @GET("sequeniatesttask/films.json")
    fun getFilms(): Call<SimpleListFilmsApiModel>
}
