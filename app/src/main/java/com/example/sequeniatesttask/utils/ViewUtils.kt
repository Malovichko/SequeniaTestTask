package com.example.sequeniatesttask.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(
    path: String,
) {
    Glide.with(this)
        .load(path)
        .into(this)
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

