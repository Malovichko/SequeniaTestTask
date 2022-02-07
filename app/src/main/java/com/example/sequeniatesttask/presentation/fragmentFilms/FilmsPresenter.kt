package com.example.sequeniatesttask.presentation.fragmentFilms

import android.os.Bundle
import android.util.Log
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Genre
import com.example.sequeniatesttask.domain.models.Title
import com.example.sequeniatesttask.domain.repository.Repository
import com.example.sequeniatesttask.utils.KEY_GENER
import javax.inject.Inject

class FilmsPresenter @Inject constructor(
    private val view: FilmsView,
    private val repository: Repository
) {

    fun onViewCreated(savedInstanceState: Bundle?) {
        val genreString = repository.getGenre()
        if (genreString != null && savedInstanceState == null) {
            var genre: Genre? = null
            repository.getFilms { films ->
                val filteredList = ArrayList<Films>()
                films.forEach { film ->
                    when (film) {
                        is FilmModel -> {}
                        is Genre -> {
                            if (film.genres == genreString) {
                                film.isChosen = true
                                genre = film
                            }
                        }
                        is Title -> {}
                    }
                    filteredList.add(film)
                }
                view.fillData(filteredList, genre)
            }
        }
        else if (savedInstanceState != null) {
            val genre = savedInstanceState.getParcelable<Genre>(KEY_GENER)
            var filmsListFiltered: MutableList<Films>
            repository.getFilms { films ->
                filmsListFiltered = if (genre == null) films as MutableList<Films> else {
                    val filteredList = ArrayList<Films>()
                    films.forEach { film ->
                        when (film) {
                            is FilmModel -> {}
                            is Genre -> {
                                if (film.genres == genre.genres) {
                                    film.isChosen = genre.isChosen
                                }
                            }
                            is Title -> {}
                        }
                        filteredList.add(film)
                    }
                    filteredList
                }
                view.fillData(filmsListFiltered, genre)
            }
        }
        else {
            repository.getFilms { films ->
                view.fillData(films, null)
            }
        }
    }

    fun saveGenre(genre: Genre?) {
        repository.saveGenre(genre)
    }

    fun onCreate() {
        repository.saveGenre(null)
    }
}