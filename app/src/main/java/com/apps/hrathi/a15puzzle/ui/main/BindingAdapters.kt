package com.apps.hrathi.a15puzzle.ui.main
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .into(view)
}

@BindingAdapter("createGrid", "bind:gridSize")
fun createGrid(gridLayout: GridLayout, viewModel: MainViewModel, gridSize : Int) {
    val displayMetrics = DisplayMetrics()
    val activity = gridLayout.context as Activity
    activity.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
    var displayWidth = displayMetrics.widthPixels
    var displayHeight = displayMetrics.heightPixels

    for (i in 0..gridSize) {
        val layoutParams = gridLayout.layoutParams
        val imageButton = ImageButton(activity).apply {
            maxWidth = displayWidth / 6
            maxHeight = displayHeight / 6
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
            setOnClickListener {
                viewModel.moveToEmpty(i)
            }
        }
        setImageSrc(imageButton, viewModel.fullBitmapLiveData, i)
        gridLayout.addView(imageButton)
    }
}


@BindingAdapter("imageSrc", "bind:index")
fun setImageSrc(view: ImageButton, liveData: LiveData<ArrayList<Bitmap?>>, index: Int) {
    liveData.observe(view.context as LifecycleOwner, Observer {
        if (it.size > 0) {
            view.setImageBitmap(it[index])
        }
    })
}

