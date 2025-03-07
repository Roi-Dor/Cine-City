package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinecity.databinding.ActivityStartUpBinding
import com.example.cinecity.utilities.AuthManager

class StartUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartUpBinding
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authManager = AuthManager.getInstance(this)

        // If user is already logged in, go to MainActivity
        if (authManager.isUserLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        // On Sign Up button click
        binding.startupBTNSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // On Login button click
        binding.startupBTNLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
