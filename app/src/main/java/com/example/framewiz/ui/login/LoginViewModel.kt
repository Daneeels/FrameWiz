package com.example.framewiz.ui.login

import androidx.lifecycle.ViewModel
import com.example.framewiz.data.FrameRepository

class LoginViewModel(private val repository: FrameRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}