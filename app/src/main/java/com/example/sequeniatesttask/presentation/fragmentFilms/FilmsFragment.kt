package com.example.sequeniatesttask.presentation.fragmentFilms

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sequeniatesttask.core.BaseFragment
import com.example.sequeniatesttask.databinding.FragmentFilmsBinding
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Gener
import com.example.sequeniatesttask.domain.models.Title
import com.example.sequeniatesttask.presentation.fragmentFilms.list.Adapter
import com.example.sequeniatesttask.presentation.fragmentFilms.list.RecyclerItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate), FilmsView {

    @Inject
    lateinit var presenter: FilmsPresenter
    private lateinit var filmAdapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }


    override fun fillData(list: List<Films>) {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        val decorator = RecyclerItemDecorator()
        filmAdapter = Adapter(list) { curIt: Films, pos: Int ->
            when (curIt) {
                is FilmModel -> {
                    val action = FilmsFragmentDirections.actionFilmsFragmentToFilmFragment(
                        curIt
                    )
                    findNavController().navigate(action)
                }
                is Gener -> {
                    filmAdapter.filter.filter(curIt.genres)
                    filmAdapter.notifyDataSetChanged()
                }
                is Title -> {
                }
            }

        }
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (Math.abs(0 - 15) + Math.abs(15 - position) == position - 0) {
                    1
                } else 2
            }
        }
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.apply {
            this.addItemDecoration(decorator)
            this.adapter = filmAdapter
            this.setHasFixedSize(true)
        }
    }
}