package com.example.sequeniatesttask.presentation.fragmentFilms.list

import androidx.recyclerview.widget.RecyclerView
import com.example.sequeniatesttask.databinding.ItemTitleBinding
import com.example.sequeniatesttask.domain.models.Title

class TitleViewHolder constructor(private val binding: ItemTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Title) {
        binding.tvTitle.text = model.title
    }
}