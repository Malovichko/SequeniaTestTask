package com.example.sequeniatesttask.presentation.fragmentFilms

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sequeniatesttask.R
import com.example.sequeniatesttask.core.BaseFragment
import com.example.sequeniatesttask.databinding.FragmentFilmsBinding
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.domain.models.Films
import com.example.sequeniatesttask.domain.models.Genre
import com.example.sequeniatesttask.domain.models.Title
import com.example.sequeniatesttask.presentation.fragmentFilms.list.Adapter
import com.example.sequeniatesttask.presentation.fragmentFilms.list.RecyclerItemDecorator
import com.example.sequeniatesttask.utils.KEY_GENER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate), FilmsView {

    @Inject
    lateinit var presenter: FilmsPresenter
    private lateinit var filmAdapter: Adapter
    private var curGener: Genre? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (curGener != null) {
            outState.putParcelable(KEY_GENER, curGener)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(savedInstanceState)
    }


    override fun fillData(list: List<Films>, gener: Genre?) {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        val decorator = RecyclerItemDecorator()
        val colors = intArrayOf(
            resources.getColor(R.color.gray_dark_3),
            resources.getColor(R.color.gray_dark_2)
        )
        filmAdapter = Adapter(list, colors) { curIt: Films, pos: Int ->
            when (curIt) {
                is FilmModel -> {
                    val action = FilmsFragmentDirections.actionFilmsFragmentToFilmFragment(
                        curIt
                    )
                    findNavController().navigate(action)
                }
                is Genre -> {
                    filtering(curIt, list)

                }
                is Title -> {
                }
            }
        }
        if (gener != null) {
            filtering(gener, list)
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

    private fun filtering(gener: Genre, list: List<Films>) {
        curGener = gener
        presenter.saveGenre(curGener)
        if (!gener.isChosen) {
            filmAdapter.setUpFilmsList(list)
            presenter.saveGenre(null)
        } else {
            filmAdapter.filter.filter(curGener!!.genres)
        }
        filmAdapter.notifyDataSetChanged()
    }
}