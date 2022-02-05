package com.example.sequeniatesttask.presentation.fragmentFilms.list

import androidx.recyclerview.widget.RecyclerView
import com.example.sequeniatesttask.databinding.ItemFilmBinding
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.utils.hide
import com.example.sequeniatesttask.utils.loadImage
import com.example.sequeniatesttask.utils.show

class FilmViewHolder constructor(private val binding: ItemFilmBinding, private val curIt: (curIt: Films, pos: Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: FilmModel) {
        binding.tvFilmTitle.text = model.localized_name
        if (model.image_url != null) {
            binding.tvHolder.hide()
            binding.ivFilmPreview.loadImage(model.image_url)
        } else binding.tvHolder.show()
        itemView.setOnClickListener {
            curIt(model, adapterPosition)
        }
    }
}