package com.example.sequeniatesttask.presentation.fragmentFilms

import com.example.sequeniatesttask.domain.repository.Repository
import javax.inject.Inject

class FilmsPresenter @Inject constructor(
    private val view: FilmsView,
    private val repository: Repository
) {
    fun onViewCreated() {
        repository.getFilms { films ->
            view.fillData(films)
        }
    }
}