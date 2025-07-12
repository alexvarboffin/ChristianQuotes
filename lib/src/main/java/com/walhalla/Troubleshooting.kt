package com.walhalla

import android.content.Context
import android.os.Environment
import com.walhalla.ui.DLog.d
import java.io.File
import java.util.Locale

object Troubleshooting {

    fun defLocation(context: Context): File? {
        val aa = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        d("@@@" + aa)
        return aa
    }

    //below 29 api
    fun withoutFileStore(context: Context): File {
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard, "Latest_quotes")
        val tmp = directory.mkdir()
        val filename = String.format(Locale.getDefault(), "%d.jpg", System.currentTimeMillis())
        return File(directory, filename)
    }
}
