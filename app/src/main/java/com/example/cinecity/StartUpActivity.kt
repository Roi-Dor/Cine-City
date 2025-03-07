package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinecity.databinding.ActivityStartUpBinding

class StartUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
