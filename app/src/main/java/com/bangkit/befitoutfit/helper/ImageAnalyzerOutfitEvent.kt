package com.bangkit.befitoutfit.helper

import android.content.Context
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.bangkit.befitoutfit.helper.BitmapExtensions.centerCrop
import com.bangkit.befitoutfit.ml.BeFitOutfitModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageAnalyzerOutfitEvent(
    private val context: Context,
    private val onResult: (String) -> Unit = { _ -> },
) : ImageAnalysis.Analyzer {
    private var frame = 0
    private val pixels = IntArray(DIMENSION * DIMENSION)
    private val input = TensorBuffer.createFixedSize(
        intArrayOf(1, DIMENSION, DIMENSION, 3),
        DataType.FLOAT32,
    )
    private val byteBuffer = ByteBuffer.allocateDirect(
        4 * DIMENSION * DIMENSION * 3,
    ).apply {
        order(
            ByteOrder.nativeOrder(),
        )
    }

    override fun analyze(
        image: ImageProxy,
    ) {
        if (frame % FRAME_LIMIT == 0) {
            var pixel = 0
            var maxPos = 0
            var maxConfidence = 0f

            image.toBitmap().centerCrop(
                cropWidth = DIMENSION,
                cropHeight = DIMENSION,
            ).getPixels(
                pixels,
                0,
                DIMENSION,
                0,
                0,
                DIMENSION,
                DIMENSION,
            )

            for (i in 0 until DIMENSION) {
                for (j in 0 until DIMENSION) {
                    val value = pixels[pixel++]
                    byteBuffer.put((value shr 16 and 0xFF).toByte())
                    byteBuffer.put((value shr 8 and 0xFF).toByte())
                    byteBuffer.put((value and 0xFF).toByte())
                }
            }

            input.loadBuffer(byteBuffer)

            val model = BeFitOutfitModel.newInstance(
                context,
                Model.Options.Builder().setNumThreads(
                    NUM_THREADS,
                ).build(),
            )


            model.process(input).outputFeature0AsTensorBuffer.floatArray.let {
                for (i in it.indices) {
                    if (it[i] > maxConfidence) {
                        maxConfidence = it[i]
                        maxPos = i
                    }
                }
            }

            model.close()

            onResult(ListOutfit.event[maxPos])
        }

        frame++

        image.close()
    }

    companion object {
        private const val DIMENSION = 150
        private const val FRAME_LIMIT = 60
        private const val NUM_THREADS = 2
    }
}
