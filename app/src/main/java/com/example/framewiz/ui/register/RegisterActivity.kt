package com.example.framewiz.ui.register

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.framewiz.R
import com.example.framewiz.ViewModelFactory
import com.example.framewiz.data.Result
import com.example.framewiz.databinding.ActivityRegisterBinding
import com.example.framewiz.ui.login.LoginViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setUpAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setUpAction() {

        var nameValid = false
        var emailValid = false
        var passwordValid = false

        binding.nameEdt.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isEmpty()) {
                        binding.nameEdtLayout.error = " "
                        binding.nameEdtLayout.setErrorIconOnClickListener {
                            Toast.makeText(
                                applicationContext,
                                "This field is required",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        binding.nameEdtLayout.error = null
                        nameValid = true
                        Log.e("name", nameValid.toString())
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    nameValid = false
                    if (s.isEmpty()) {
                        binding.nameEdtLayout.error = " "
                        binding.nameEdtLayout.setErrorIconOnClickListener {
                            Toast.makeText(
                                applicationContext,
                                "This field is required",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        binding.nameEdtLayout.error = null
                        nameValid = true
                        Log.e("name", nameValid.toString())
                    }
                }
            }
        )

        binding.emailEdt.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isNotEmpty()) {
                        binding.emailEdtLayout.error = null

                        if (!isValidEmail(s)) {
                            binding.emailEdtLayout.error = " "
                            binding.emailEdtLayout.setErrorIconOnClickListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Invalid email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            binding.emailEdtLayout.error = null
                            emailValid = true
                            Log.e("email", emailValid.toString())
                        }
                    } else {
                        binding.emailEdtLayout.error = " "
                        binding.emailEdtLayout.setErrorIconOnClickListener {
                            Toast.makeText(
                                applicationContext,
                                "This field is required",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    emailValid = false
                    if (s.isNotEmpty()) {
                        binding.emailEdtLayout.error = null

                        if (!isValidEmail(s)) {
                            binding.emailEdtLayout.error = " "
                            binding.emailEdtLayout.setErrorIconOnClickListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Invalid email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            binding.emailEdtLayout.error = null
                            emailValid = true
                            Log.e("email", emailValid.toString())
                        }
                    } else {
                        binding.emailEdtLayout.error = " "
                        binding.emailEdtLayout.setErrorIconOnClickListener {
                            Toast.makeText(
                                applicationContext,
                                "This field is required",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        )

        binding.passwordEdt.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.isNotEmpty()) {
                        binding.passwordEdtLayout.error = null
                        if (s.length < 8) {
                            binding.passwordEdtLayout.error = " "
                            binding.passwordEdtLayout.setErrorIconOnClickListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Password must contain at least 8 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            binding.passwordEdtLayout.error = null
                            passwordValid = true
                            Log.e("password", passwordValid.toString())
                        }
                    } else {
                        binding.passwordEdtLayout.error = " "
                        binding.passwordEdtLayout.setErrorIconOnClickListener {
                            Toast.makeText(
                                applicationContext,
                                "This field is required",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    passwordValid = false
                    if (s.isNotEmpty()) {
                        binding.passwordEdtLayout.error = null
                        if (s.length < 8) {
                            binding.passwordEdtLayout.error = " "
                            binding.passwordEdtLayout.setErrorIconOnClickListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Password must contain at least 8 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            binding.passwordEdtLayout.error = null
                            passwordValid = true
                            Log.e("password", passwordValid.toString())
                        }
                    } else {
                        binding.passwordEdtLayout.error = " "
                        binding.passwordEdtLayout.setErrorIconOnClickListener {
                            Toast.makeText(
                                applicationContext,
                                "This field is required",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        )

        binding.registerBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val nama = binding.nameEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            if (nameValid && emailValid && passwordValid) {
                registerViewModel.register(email, nama, password).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Error -> Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            is Result.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                            is Result.Success -> {
                                Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Belum amang sam", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}