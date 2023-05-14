package com.evreydima.camerapeople.scanActvivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.evreydima.camerapeople.R
import com.evreydima.camerapeople.services.ImageAnalyzer
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.CompletableFuture

class ScanActivity: AppCompatActivity() {

    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var cameraFuture: ListenableFuture<ProcessCameraProvider>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
       cameraFuture =  ProcessCameraProvider.getInstance(this)
        cameraFuture.addListener({
            cameraProvider = cameraFuture.get()
            bindPreview()
        },ContextCompat.getMainExecutor(this))

    }
    private fun bindPreview(){
        val preview = Preview.Builder().build()
        val previewSurface = findViewById<PreviewView>(R.id.preview_J)
        preview.setSurfaceProvider(previewSurface.surfaceProvider)

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val analizer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        analizer.setAnalyzer( mainExecutor,
            ImageAnalyzer()
        )
        cameraProvider?.bindToLifecycle(this,cameraSelector,preview, analizer)
    }
}