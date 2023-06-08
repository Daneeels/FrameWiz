package com.example.framewiz.ui.register

import androidx.lifecycle.ViewModel
import com.example.framewiz.data.FrameRepository

class RegisterViewModel(private val repository: FrameRepository) : ViewModel() {
    fun register(email: String, nama: String, password: String) = repository.register(email, nama, password)
}