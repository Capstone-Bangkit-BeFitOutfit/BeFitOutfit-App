package com.bangkit.befitoutfit.helper

import android.content.Context
import android.graphics.Bitmap
import android.view.Surface
import com.bangkit.befitoutfit.data.model.Classification
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions.Orientation
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

// TODO: TfLiteEventTypeClassifier
class TfLiteLandMarkClassifier(
    private val context: Context,
    private val scoreThreshold: Float = 0.5f,
    private val maxResults: Int = 1,
) {
    private var classifier: ImageClassifier? = null

    private fun setupClassifier() {
        val baseOptions = BaseOptions.builder().setNumThreads(
            2,
        ).build()

        val options = ImageClassifier.ImageClassifierOptions.builder().setBaseOptions(
            baseOptions,
        ).setMaxResults(
            maxResults,
        ).setScoreThreshold(
            scoreThreshold,
        ).build()

        try {
            classifier = ImageClassifier.createFromFileAndOptions(
                context,
                "landmarks-europe.tflite",
                options,
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun classify(
        bitmap: Bitmap,
        rotation: Int,
    ): List<Classification> {
        if (classifier == null) {
            setupClassifier()
        }

        val imageProcessor = ImageProcessor.Builder().build()

        val tensorImage = imageProcessor.process(
            TensorImage.fromBitmap(
                bitmap,
            ),
        )

        val imageProcessingOptions = ImageProcessingOptions.builder().setOrientation(
            when (rotation) {
                Surface.ROTATION_0 -> Orientation.BOTTOM_RIGHT
                Surface.ROTATION_90 -> Orientation.TOP_LEFT
                Surface.ROTATION_180 -> Orientation.RIGHT_BOTTOM
                else -> Orientation.RIGHT_TOP
            },
        ).build()

        val results = classifier?.classify(tensorImage, imageProcessingOptions)

        return results?.flatMap { classifications ->
            classifications.categories.map { category ->
                Classification(
                    name = category.displayName,
                    score = category.score,
                )
            }
        }?.distinctBy {
            it.name
        } ?: emptyList()
    }
}
