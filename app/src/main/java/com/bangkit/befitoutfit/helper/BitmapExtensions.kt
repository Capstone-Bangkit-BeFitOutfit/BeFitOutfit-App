package com.bangkit.befitoutfit.helper

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

object BitmapExtensions {
    fun Bitmap?.toJpeg(): File? {
        val bytes = ByteArrayOutputStream()

        try {
            this?.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
            val file = File.createTempFile("image", ".jpg").apply {
                createNewFile()
            }

            file.outputStream().apply {
                write(bytes.toByteArray())
                close()
            }

            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}
