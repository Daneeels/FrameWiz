package com.example.framewiz.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.framewiz.R
import com.example.framewiz.ViewModelFactory
import com.example.framewiz.data.Result
import com.example.framewiz.data.api.LoginResponse
import com.example.framewiz.databinding.ActivityLoginBinding
import com.example.framewiz.ui.main.MainActivity
import com.example.framewiz.ui.register.RegisterActivity
import java.io.File

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(this)
    }

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

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupView()
        goToRegister()
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

        var emailValid = false
        var passwordValid = false


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

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            if (emailValid && passwordValid) {
                loginViewModel.login(email, password) .observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Error -> {
                                showLoading(false)
                                Log.e("LoginViewModel", result.error)
                            }
                            is Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showLoading(false)
                                saveKey(result.data)
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

    private fun goToRegister(){
        binding.signUpNavigation.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveKey(data: LoginResponse) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}