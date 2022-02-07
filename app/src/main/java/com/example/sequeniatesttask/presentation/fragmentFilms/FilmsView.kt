package com.example.sequeniatesttask.presentation.fragmentFilms

import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Genre

interface FilmsView {
    fun fillData(list: List<Films>, gener: Genre?)
}