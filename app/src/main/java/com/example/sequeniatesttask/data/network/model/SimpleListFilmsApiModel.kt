package com.example.sequeniatesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class SimpleListFilmsApiModel(
    @SerializedName("films")
    val films: List<SimpleFilmApiModel>
)