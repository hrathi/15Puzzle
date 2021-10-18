package com.apps.hrathi.a15puzzle.ui.main

import android.graphics.Bitmap
import android.util.DisplayMetrics
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.hrathi.a15puzzle.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.Throws

class MainViewModel : ViewModel() {
    private val imageRepository = ImageRepository()
    var randomImageUrl = MutableLiveData<String>()

    private val bitmapList = ArrayList<Bitmap?>(16) // hardcoded for 4x4 board
    val fullBitmapLiveData = MutableLiveData(bitmapList)

    @Throws(Exception::class)
    fun fetchRandomImage() {
        // TODO: Find why Android logcat showing that we are doing too much work on main thread.
        viewModelScope.launch(Dispatchers.IO) {
            val url = imageRepository.getImageUrl()
            randomImageUrl.postValue(url)
        }
    }

    fun shuffleImage(fullBitmap: Bitmap, width: Int, height: Int) {
        val chunkWidth = width / 4
        val chunkHeight = height / 4

        val scaledBitmap = Bitmap.createScaledBitmap(fullBitmap, width, height, true)

        bitmapList.clear()
        var yPos = 0
        for(i in 0..3) {
            var xPos = 0
            for (j in 0..3) {
                if (i == 3 && j == 3) {
                    bitmapList.add(null);
                    continue;
                }
                bitmapList.add(Bitmap.createBitmap(scaledBitmap, xPos, yPos, chunkWidth,
                    chunkHeight))
                xPos += chunkWidth
            }
            yPos += chunkHeight
        }

        bitmapList.shuffle()
        fullBitmapLiveData.postValue(bitmapList)
    }

    // TODO: This code needs to be optimized and made to work for all cases.
    // TODO: Also add code when the puzzle is "solved".
    fun moveToEmpty(x : Int) {
        when {
            bitmapList.size > (x+1) && bitmapList[x+1] == null -> {
                bitmapList[x+1] = bitmapList[x]
                bitmapList[x] = null
            }
            0 >= (x-1) && bitmapList[x-1] == null -> {
                bitmapList[x-1] = bitmapList[x]
                bitmapList[x] = null
            }
            bitmapList.size > (x+4) && bitmapList[x+4] == null -> {
                bitmapList[x+4] = bitmapList[x]
                bitmapList[x] = null
            }
            0 >= (x-4) && bitmapList[x-4] == null -> {
                bitmapList[x-4] = bitmapList[x]
                bitmapList[x] = null
            }
        }

        fullBitmapLiveData.postValue(bitmapList)
    }

    companion object {
        @JvmField val SHUFFLE_SIZE = 16
    }
}