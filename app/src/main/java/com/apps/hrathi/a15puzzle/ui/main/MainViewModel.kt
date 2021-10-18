package com.apps.hrathi.a15puzzle.ui.main

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.hrathi.a15puzzle.ImageRepository
import com.apps.hrathi.a15puzzle.ImageTile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.Throws
import kotlin.math.sqrt

class MainViewModel : ViewModel() {
    private val imageRepository = ImageRepository()
    var randomImageUrl = MutableLiveData<String>()

    private val bitmapList = ArrayList<ImageTile?>(SHUFFLE_SIZE) // hardcoded for 4x4 board
    val fullBitmapLiveData = MutableLiveData(bitmapList)

    @Throws(Exception::class)
    fun fetchRandomImage() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = imageRepository.getImageUrl()
            randomImageUrl.postValue(url)
        }
    }

    fun shuffleImage(fullBitmap: Bitmap, width: Int, height: Int) {
        val scaledWidth = (width * 0.7).toInt()
        val scaledHeight = (height * 0.7).toInt()

        val chunkWidth = scaledWidth / rows
        val chunkHeight = scaledHeight / columns

        val scaledBitmap = Bitmap.createScaledBitmap(fullBitmap, scaledWidth, scaledHeight, true)

        bitmapList.clear()
        var index = 0;
        var yPos = 0
        for(i in 0 until rows) {
            var xPos = 0
            for (j in 0 until columns) {
                if (i == (rows - 1) && j == (columns - 1)) {
                    bitmapList.add(ImageTile(null, index));
                    continue;
                }
                bitmapList.add(ImageTile(Bitmap.createBitmap(scaledBitmap, xPos, yPos, chunkWidth,
                    chunkHeight), index++))
                xPos += chunkWidth
            }
            yPos += chunkHeight
        }

        bitmapList.shuffle()
        fullBitmapLiveData.postValue(bitmapList)
    }

    fun sort() {
        bitmapList.sortBy {
            it?.originalIndex
        }

        fullBitmapLiveData.postValue(bitmapList)
    }

    // TODO: Also add code when the puzzle is "solved".
    fun moveToEmpty(x : Int) {
        when {
            bitmapList.size > (x+1) && bitmapList[x+1] == null -> {
                bitmapList[x+1] = bitmapList[x]
                bitmapList[x] = null
            }
            (x-1) >= 0 && bitmapList[x-1] == null -> {
                bitmapList[x-1] = bitmapList[x]
                bitmapList[x] = null
            }
            bitmapList.size > (x+rows) && bitmapList[x+rows] == null -> {
                bitmapList[x+rows] = bitmapList[x]
                bitmapList[x] = null
            }
            (x-rows) >= 0 && bitmapList[x-rows] == null -> {
                bitmapList[x-4] = bitmapList[x]
                bitmapList[x] = null
            }
        }

        fullBitmapLiveData.postValue(bitmapList)
    }

    companion object {
        @JvmField val SHUFFLE_SIZE = 16
        val rows = sqrt(SHUFFLE_SIZE.toDouble()).toInt()
        val columns = rows
    }
}