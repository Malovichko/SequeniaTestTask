package com.example.sequeniatesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class SimpleFilmApiModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("localized_name")
    val localized_name: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("year")
    val year: Int
)