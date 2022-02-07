package com.example.sequeniatesttask.data

import android.util.Log
import com.example.sequeniatesttask.data.network.FilmsApiService
import com.example.sequeniatesttask.data.network.model.SimpleListFilmsApiModel
import com.example.sequeniatesttask.data.preferences.SharedPref
import com.example.sequeniatesttask.domain.mappers.SimpleFilmsMapper
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Genre
import com.example.sequeniatesttask.domain.models.Title
import com.example.sequeniatesttask.domain.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val filmsApiService: FilmsApiService, private val sharedPref: SharedPref) :
    Repository {
    override fun getFilms(getData: (list: List<Films>) -> Unit) {
        filmsApiService.getFilms().enqueue(object : Callback<SimpleListFilmsApiModel> {
            override fun onResponse(
                call: Call<SimpleListFilmsApiModel>,
                response: Response<SimpleListFilmsApiModel>
            ) {
                if (response.isSuccessful) {
                    val filmModelList  = mutableListOf<FilmModel>()
                    filmModelList.addAll(SimpleFilmsMapper.mapApiToDomain(response.body()!!))
                    filmModelList.sortWith(
                        compareBy(String.CASE_INSENSITIVE_ORDER, { filmModel ->
                            val myLocale = Locale("ru", "RU")
                            filmModel.localized_name.lowercase(myLocale)
                        })
                    )
                    val allGenresList = mutableListOf<String>()
                    filmModelList.forEach { filmModel ->
                        allGenresList += filmModel.genres
                    }
                    val genreList = SimpleFilmsMapper.mapStringToGener(allGenresList.toSet())

                    val outList = mutableListOf<Films>()
                    outList.add(0, Title("Жанры"))
                    outList += genreList
                    outList.add(Title("Фильмы"))
                    outList += filmModelList
                    getData(outList)
                }
            }

            override fun onFailure(call: Call<SimpleListFilmsApiModel>, t: Throwable) {
                Log.d("RepositoryImpl", "onFailure")
            }
        })
    }

    override fun getGenre(): String? {
        return sharedPref.getGenre()
    }

    override fun saveGenre(gener: Genre?) {
       sharedPref.setGenre(gener?.genres)
    }

}