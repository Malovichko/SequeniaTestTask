package com.example.sequeniatesttask.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.sequeniatesttask.R

fun ImageView.loadImage(
    path: String,
) {
    Glide.with(this)
        .load(path)
        .error(R.drawable.ic_baseline_no_photography_24)
        .into(this)
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

