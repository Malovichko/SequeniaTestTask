package com.example.sequeniatesttask.domain.repository

import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Gener

interface Repository {
    fun getFilms(getData: (list: List<Films>) -> Unit)
}