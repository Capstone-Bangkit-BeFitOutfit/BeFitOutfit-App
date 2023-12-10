package com.bangkit.befitoutfit.helper

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.bangkit.befitoutfit.helper.BitmapExtensions.centerCrop

class LandmarkImageAnalyzer(
    private val classifier: TfLiteLandMarkClassifier,
    private val onResultsDummy: (String) -> Unit = { _ -> },
) : ImageAnalysis.Analyzer {
    private var frameCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees

            val bitmap = image.toBitmap().centerCrop(
                cropWidth = 321,
                cropHeight = 321,
            )

            val result = classifier.classify(bitmap, rotationDegrees)

            onResultsDummy("[${result[0].score}] ${result[0].name}")
        }

        frameCounter++

        image.close()
    }
}
