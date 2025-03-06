package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinecity.databinding.ActivityPostProgramBinding
import com.example.cinecity.utilities.ImageLoader

class PostProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProgramBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostProgramBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            val posterUrl = bundle.getString("poster")
            val programName = bundle.getString("name")

            // Example: load poster with your image utility
            if (posterUrl != null) {
                ImageLoader.getInstance().loadImage(posterUrl, binding.postProgramIMGPoster)
            }

            binding.appLogo.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: Close FriendsSearch activity
            }
            // Set the program name
            binding.postProgramLBLName.text = programName


            // Or if you want to pass the rating along when user clicks Submit:
//        binding.postProgramBTNSubmit.setOnClickListener {
//            val chosenRating = binding.postProgramRBRating.rating
//            // ... do something with chosenRating ...
//        }
        }
    }
}