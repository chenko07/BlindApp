// MainActivity.kt

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener  {

    private lateinit var buttonCapture: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonRotateText: Button
    private lateinit var buttonLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi UI components
        buttonCapture = findViewById(R.id.buttonCapture)
        textViewResult = findViewById(R.id.textViewResult)
        buttonRotateText = findViewById(R.id.buttonRotateText)
        buttonLogout = findViewById(R.id.buttonLogout)

        // Set listener untuk button capture
        buttonCapture.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val userDao = MyApp.database.userDao()
                // Contoh operasi-insert
                userDao.insertUser(User(username = "john_doe", password = "password123"))
            }
            captureImage()
        }

        // Set listener untuk button capture
        buttonCapture.setOnClickListener {
            captureImage()
        }

        // Set listener untuk button putar teks
        buttonRotateText.setOnClickListener {
            rotateText()
        }

        // Set listener untuk button logout
        buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(findViewById<PreviewView>(R.id.previewView).surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                // Handle exceptions
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: photoFile.toUri()
                    val msg = "Foto berhasil disimpan di $savedUri"
                    textViewResult.text = msg
                }

                override fun onError(exception: ImageCaptureException) {
                    val msg = "Gagal menyimpan foto: ${exception.message}"
                    textViewResult.text = msg
                }
            })
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun speakText(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun logout() {
        // Tambahkan kode untuk logout
        // (Anda perlu mengimplementasikan logika logout sesuai kebutuhan)
        finish() // Menutup activity MainActivity
    }
}

