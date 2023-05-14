package com.evreydima.camerapeople.services

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ImageAnalyzer: ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        println("я террорист")


        image.close()
    }
}