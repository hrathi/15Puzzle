package com.apps.hrathi.a15puzzle.ui.main
import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.util.TypedValue
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.view.setPadding
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
        val imageButton = Button(activity).apply {
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
            setOnClickListener {
                viewModel.moveToEmpty(i)
            }
            setTextSize(TypedValue.COMPLEX_UNIT_PX, 100F)
        }
        setImageSrc(imageButton, viewModel.fullBitmapLiveData, i)
        gridLayout.useDefaultMargins = true
        gridLayout.addView(imageButton)
    }
}

@BindingAdapter("imageSrc", "bind:index")
fun setImageSrc(view: Button, liveData: LiveData<ArrayList<ImageTile>>, index: Int) {
    liveData.observe(view.context as LifecycleOwner, Observer {
        if (it.size > 0) {
            val bitmapDrawable = BitmapDrawable(view.resources, it[index].bitmap)
            view.background = bitmapDrawable

            val newIndex = it[index].newIndex
            view.text = if(newIndex >= 0) newIndex.toString() else ""
        }
    })
}

