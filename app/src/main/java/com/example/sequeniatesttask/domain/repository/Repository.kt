package com.example.sequeniatesttask.domain.repository

import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Genre

interface Repository {
    fun getFilms(getData: (list: List<Films>) -> Unit)
    fun getGenre() : String?
    fun saveGenre(gener: Genre?)
}