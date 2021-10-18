package com.apps.hrathi.a15puzzle

import android.graphics.Bitmap

data class ImageTile(var bitmap: Bitmap?, val originalIndex: Int) {
    var newIndex = originalIndex

    val isEmpty: Boolean
        get() {
            return bitmap == null
        }
}
