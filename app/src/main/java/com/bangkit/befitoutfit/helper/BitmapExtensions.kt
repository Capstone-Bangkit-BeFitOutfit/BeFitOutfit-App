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

    fun Bitmap.centerCrop(cropWidth: Int, cropHeight: Int): Bitmap {
        val x = (width - cropWidth) / 2
        val y = (height - cropHeight) / 2

        if (x < 0 || y < 0 || cropWidth > width || cropHeight > height) throw IllegalArgumentException(
            "Invalid arguments for center cropping\nwidth: $width\nheight: $height\ncropWidth: $cropWidth\ncropHeight: $cropHeight",
        )

        return Bitmap.createBitmap(
            this,
            x,
            y,
            cropWidth,
            cropHeight,
        )
    }
}
