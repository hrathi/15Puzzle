package com.apps.hrathi.a15puzzle.ui.main
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.apps.hrathi.a15puzzle.ImageTile

import com.squareup.picasso.Picasso
import kotlin.math.sqrt

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .into(view)
}

@BindingAdapter("createGrid", "bind:gridSize")
fun createGrid(gridLayout: GridLayout, viewModel: MainViewModel, gridSize : Int) {
    val activity = gridLayout.context as Activity

    gridLayout.rowCount = sqrt(gridSize.toDouble()).toInt()
    gridLayout.columnCount = gridLayout.rowCount

    for (i in 0 until gridSize) {
        val layoutParams = gridLayout.layoutParams
        val imageButton = ImageButton(activity).apply {
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
            maxWidth = 10
            maxHeight = 10
            setOnClickListener {
                viewModel.moveToEmpty(i)
            }
        }
        setImageSrc(imageButton, viewModel.fullBitmapLiveData, i)
        gridLayout.addView(imageButton)
    }
}

@BindingAdapter("imageSrc", "bind:index")
fun setImageSrc(view: ImageButton, liveData: LiveData<ArrayList<ImageTile?>>, index: Int) {
    liveData.observe(view.context as LifecycleOwner, Observer {
        if (it.size > 0) {
            view.setImageBitmap(it[index]?.bitmap)
        }
    })
}

