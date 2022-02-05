package com.example.sequeniatesttask.presentation.fragmentFilm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sequeniatesttask.R
import com.example.sequeniatesttask.core.BaseFragment
import com.example.sequeniatesttask.databinding.FragmentFilmBinding
import com.example.sequeniatesttask.domain.models.FilmModel
import com.example.sequeniatesttask.utils.loadImage

class FilmFragment : BaseFragment<FragmentFilmBinding>(FragmentFilmBinding::inflate) {

    private val args: FilmFragmentArgs by navArgs()
    private lateinit var res: FilmModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        res = args.film
        initViews()
        initListener()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        binding.apply {
            res.image_url?.let { ivPreview.loadImage(it) }
            tvDescribe.text = res.description
            tvTitle.text = res.name
            tvRating.text = "${resources.getText(R.string.rating)}:  ${res.rating}"
            tvYear.text = "${resources.getText(R.string.year)}: ${res.year}"
            toolbar.apply {
                title = res.localized_name
                navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_24, null)
            }
        }

    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}