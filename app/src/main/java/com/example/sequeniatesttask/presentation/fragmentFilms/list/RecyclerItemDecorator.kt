package com.example.sequeniatesttask.presentation.fragmentFilms.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 8
        outRect.right = 8
        outRect.left = 8
        outRect.bottom = 8
    }
}