package com.example.sequeniatesttask.presentation.fragmentFilms

import com.example.sequeniatesttask.domain.models.Films

interface FilmsView {
    fun fillData(list: List<Films>)
}