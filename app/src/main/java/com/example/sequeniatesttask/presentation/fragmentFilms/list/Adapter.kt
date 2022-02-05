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
import com.example.sequeniatesttask.domain.models.Gener
import com.example.sequeniatesttask.domain.models.Title
import okhttp3.internal.notifyAll

class Adapter constructor(
    private var films: List<Films>,
    private val curIt: (curIt: Films, pos: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    companion object {
        private const val SQUARE_ITEM = 0
        private const val RECTANGULAR_ITEM = 1
        private const val TITLE_ITEM = 2
    }

    var filmsListFiltered: MutableList<Films> = mutableListOf()

    var onItemClick: ((Films) -> Unit)? = null

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
        when (val currentItem = films[position]) {
            is FilmModel -> (holder as FilmViewHolder).bind(currentItem)
            is Gener -> (holder as GenerViewHolder).bind(currentItem)
            is Title -> (holder as TitleViewHolder).bind(currentItem)
        }
    }

    override fun getItemCount(): Int = films.size

    override fun getItemViewType(position: Int): Int {
        return when (films[position]) {
            is Gener -> RECTANGULAR_ITEM
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
                if (gener.isEmpty()) filmsListFiltered = films as MutableList<Films> else {
                    val filteredList = ArrayList<Films>()
                    films
                        .filter {
                            when (it) {
                                is FilmModel -> {
                                    (it.genres.contains(constraint!!))
                                }
                                is Gener -> true
                                is Title -> true
                            }
                        }
                        .forEach { filteredList.add(it) }
                    filmsListFiltered = filteredList

                    Log.e("performFiltering: t1: ", filmsListFiltered.size.toString())
                }
                return FilterResults().apply { values = filmsListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filmsListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Films>
                notifyDataSetChanged()
                Log.e("performFiltering: t2 ", "called" + filmsListFiltered.size)
            }
        }
    }

    fun updateList(list: List<Films>) {
        filmsListFiltered.clear()
        notifyDataSetChanged()
        Log.d("TAG", "updateList")
        filmsListFiltered.addAll(list)
        Log.d("TAG", "updateList2")
        notifyDataSetChanged()
    }

    fun filter(model: Gener): List<Films> {
        val temp = mutableListOf<Films>()
        filmsListFiltered.forEach {
            when (it) {
                is FilmModel -> {
                    if (it.genres.contains(model.genres)) temp.add(it)
                }
                is Gener -> {
                    temp.add(it)
                }
                is Title -> {
                    temp.add(it)
                }
            }
        }
        return temp
    }


    inner class GenerViewHolder constructor(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Gener) {
            binding.btnGener.text = model.genres
            itemView.setOnClickListener {
                curIt(model, adapterPosition)
//                filter(model)
                Log.d("TAG", "bind: ${model.genres}")
                notifyDataSetChanged()
            }
        }
    }
}

