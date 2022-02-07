package com.example.sequeniatesttask.presentation.fragmentFilms.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.sequeniatesttask.databinding.ItemFilmBinding
import com.example.sequeniatesttask.databinding.ItemGenresBinding
import com.example.sequeniatesttask.databinding.ItemTitleBinding
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Genre
import com.example.sequeniatesttask.domain.models.Title
import android.content.res.ColorStateList


class Adapter constructor(

    private var films: List<Films>,
    private val colorStateList: IntArray,
    private val curIt: (curIt: Films, pos: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    companion object {
        private const val SQUARE_ITEM = 0
        private const val RECTANGULAR_ITEM = 1
        private const val TITLE_ITEM = 2
    }

    var filmsListFiltered: MutableList<Films> = mutableListOf()

    init {
        filmsListFiltered.addAll(films)
    }

    fun setUpFilmsList(list: List<Films>) {
        filmsListFiltered.clear()
        filmsListFiltered.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SQUARE_ITEM -> {
                val binding =
                    ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FilmViewHolder(binding, curIt)
            }
            RECTANGULAR_ITEM -> {
                val binding =
                    ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GenerViewHolder(binding)
            }
            TITLE_ITEM -> {
                val binding =
                    ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
            }
            else -> throw ClassCastException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val currentItem = filmsListFiltered[position]) {
            is FilmModel -> (holder as FilmViewHolder).bind(currentItem)
            is Genre -> (holder as GenerViewHolder).bind(currentItem)
            is Title -> (holder as TitleViewHolder).bind(currentItem)
        }
    }

    override fun getItemCount(): Int = filmsListFiltered.size

    override fun getItemViewType(position: Int): Int {
        return when (filmsListFiltered[position]) {
            is Genre -> RECTANGULAR_ITEM
            is FilmModel -> SQUARE_ITEM
            is Title -> TITLE_ITEM
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val gener = constraint?.toString() ?: ""
                filmsListFiltered = if (gener.isEmpty()) films as MutableList<Films> else {
                    val filteredList = ArrayList<Films>()
                    films
                        .filter {
                            when (it) {
                                is FilmModel -> {
                                    (it.genres.contains(constraint!!))
                                }
                                is Genre -> true
                                is Title -> true
                            }
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = filmsListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filmsListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Films>
                notifyDataSetChanged()
            }
        }
    }


    inner class GenerViewHolder constructor(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(model: Genre) {
            Log.d("TAG", "bind: $model")
            if (model.isChosen) {
                itemView.backgroundTintList = ColorStateList.valueOf(colorStateList[0])

            } else {
                itemView.backgroundTintList = ColorStateList.valueOf(colorStateList[1])
            }

            binding.btnGener.text = model.genres
            itemView.setOnClickListener {


                filmsListFiltered.forEach {
                    when (it) {
                        is FilmModel -> {}
                        is Genre -> {
                            if (it.isChosen && it != model) {
                                it.isChosen = false
                            }
                            else if (it == model) {
                                model.isChosen = !model.isChosen
                                it.isChosen = model.isChosen
                            }
                        }
                        is Title -> {}
                    }
                }
                curIt(model, adapterPosition)
                notifyDataSetChanged()
            }
        }
    }
}

