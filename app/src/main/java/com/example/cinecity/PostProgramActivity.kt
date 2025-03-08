package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinecity.databinding.ActivityPostProgramBinding
import com.example.cinecity.models.Program
import com.example.cinecity.utilities.FirebaseManager
import com.example.cinecity.utilities.ImageLoader
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import java.time.LocalDate

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
            val length = bundle.getInt("length")
            val overview = bundle.getString("overview")
            val releaseDate = bundle.getString("releaseDate")


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
        binding.postProgramBTNSubmit.setOnClickListener {
            addProgramToUser(posterUrl, programName,length,overview,releaseDate)
        }
        }
    }
    private fun addProgramToUser(posterUrl: String?, programName: String?, length: Int?, overview: String?, releaseDate: String?) {
        val rating = binding.postProgramRBRating.rating

        val newProgram = Program.Builder()
            .poster(posterUrl ?: "")
            .name(programName ?: "Unknown")
            .overview(overview ?: "Unknown")
            .length(length ?: 0)
            .rating(rating)
            .build()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(this, "You must be logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseManager.getInstance().addProgramToUser(
            currentUser.uid,
            newProgram,
            object : FirebaseManager.FirebaseCallback {
                override fun onSuccess() {
                    Toast.makeText(this@PostProgramActivity, "Program added!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PostProgramActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(errorMessage: String) {
                    Toast.makeText(this@PostProgramActivity, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

}