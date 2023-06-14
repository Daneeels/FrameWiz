package com.example.framewiz.ui.main

import androidx.lifecycle.ViewModel
import com.example.framewiz.data.FrameRepository
import okhttp3.MultipartBody

class MainViewModel(private val repository: FrameRepository): ViewModel() {

    fun predict(
        image: MultipartBody.Part
    ) = repository.predict(image)
}