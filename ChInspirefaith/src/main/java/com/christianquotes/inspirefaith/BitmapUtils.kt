package com.christianquotes.inspirefaith

import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.RelativeLayout
import androidx.core.graphics.createBitmap

object BitmapUtils {
    fun getBitmapFromImageView(parent: RelativeLayout): Bitmap? {
        val height = parent.height
        val width = parent.width
        var bitmap: Bitmap? = null
        if (height > 0) {
            bitmap = createBitmap(width, height)
            val canvas = Canvas(bitmap)
            parent.draw(canvas)
        }
        return bitmap
    }
}
