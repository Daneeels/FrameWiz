package com.example.framewiz.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.framewiz.ViewModelFactory
import com.example.framewiz.data.FrameData
import com.example.framewiz.data.FrameDatas
import com.example.framewiz.data.FrameResponse
import com.example.framewiz.data.Result
import com.example.framewiz.databinding.ActivityMainBinding
import com.example.framewiz.getImageUriFromBitmap
import com.example.framewiz.reduceFileImage
import com.example.framewiz.rotateBitmap
import com.example.framewiz.ui.camera.CameraActivity
import com.example.framewiz.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var binding: ActivityMainBinding
    private var getFile: File? = null


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val LOCATION_PERMISSION_REQ_CODE = 1000

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            val resultUri = getImageUriFromBitmap(this, result)
            getFile = uriToFile(resultUri, this)

            binding.imageUser.setImageBitmap(result)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        startCamera()



        binding.btnUpload.setOnClickListener {
            uploadFile(getFile)
        }
    }

    private fun uploadFile(file: File?) {
        if (file != null) {
             val imageFile = reduceFileImage(file)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            mainViewModel.predict(imageMultipart).observe(this){result->
                if (result != null){
                    when(result){
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            FrameDatas.frame.forEach { shape->
                                Log.e("Frame", shape.shape)
                                if (result.data.predicted_class.lowercase() == shape.shape.lowercase()){
                                    Toast.makeText(this, shape.shape, Toast.LENGTH_SHORT).show()
                                    runRV(shape.list)
                                }
                            }

                        }
                    }
                }
            }
        }

    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun runRV(frameList: List<FrameData>) {
        binding.frameRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = MainAdapter(frameList)
        binding.frameRV.adapter = adapter

    }
}