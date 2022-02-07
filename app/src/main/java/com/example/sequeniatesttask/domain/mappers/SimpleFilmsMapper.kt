package com.example.sequeniatesttask.domain.mappers

import com.example.sequeniatesttask.data.network.model.SimpleListFilmsApiModel
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Genre

object SimpleFilmsMapper {
    fun mapApiToDomain(source: SimpleListFilmsApiModel): List<FilmModel> {
        val filmsListModel = mutableListOf<FilmModel>()
        source.films.forEach { simpleFilmApiModel ->
            val domainModel = FilmModel(
                simpleFilmApiModel.description,
                simpleFilmApiModel.genres,
                simpleFilmApiModel.id,
                simpleFilmApiModel.image_url,
                simpleFilmApiModel.localized_name,
                simpleFilmApiModel.name,
                simpleFilmApiModel.rating,
                simpleFilmApiModel.year
            )
            filmsListModel.add(domainModel)
        }
        return filmsListModel
    }

    fun mapStringToGener(source: Set<String>) : List<Genre> {
        val genersList = mutableListOf<Genre>()
        source.forEach { str ->
            val gener = Genre(
                str,
                false
            )
            genersList.add(gener)
        }
        return  genersList
    }
}