package com.example.sequeniatesttask.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Films
@Parcelize
data class FilmModel(
    val description: String?,
    val genres: List<String>,
    val id: Int,
    val image_url: String?,
    val localized_name: String,
    val name: String,
    val rating: Double,
    val year: Int
) : Films(), Parcelable

@Parcelize
data class Genre(
    val genres: String,
    var isChosen: Boolean
) : Films(), Parcelable

data class Title(
    val title: String
) : Films()
