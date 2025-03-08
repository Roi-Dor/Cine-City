package com.example.cinecity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinecity.databinding.ActivityRegisterBinding
import com.example.cinecity.models.User
import com.example.cinecity.utilities.AuthManager
import com.example.cinecity.utilities.FirebaseManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authManager: AuthManager
    private lateinit var firestoreManager: FirebaseManager
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseStorage = FirebaseStorage.getInstance()
        Log.d("FirebaseStorage", "Current bucket: ${firebaseStorage.reference.bucket}")

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authManager = AuthManager.getInstance(this)
        firestoreManager = FirebaseManager.getInstance()

        initViews()
    }

    // Initialize button click events for the registration page
    private fun initViews() {
        binding.registerBTNSubmit.setOnClickListener { registerUser() }
        binding.registerBTNBack.setOnClickListener { finish() }
        binding.uploadBTNImage.setOnClickListener { uploadImage() }
    }

    // Launch an intent to pick an image for the user's profile picture
    private fun uploadImage() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Choose Profile Picture"), REQUEST_CODE_PICK_IMAGE)
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 0
    }

    private fun registerUser() {
        val firstName = binding.registerETFirstName.editText?.text.toString().trim()
        val lastName = binding.registerETLastName.editText?.text.toString().trim()
        val email = binding.registerETEmail.editText?.text.toString().trim()
        val password = binding.registerETPassword.editText?.text.toString().trim()

        // Validate that all fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (fileUri == null) {
            Log.w("RegisterActivity", "No profile picture selected. Proceeding without profile image.")
        } else {
            Log.d("FirebaseStorage", "Uploading file from URI: $fileUri")
        }

        // Create a new user account using AuthManager
        authManager.createUser(email, password, object : AuthManager.AuthCallback {
            override fun onSuccess(user: FirebaseUser?) {
                user?.uid?.let { userId ->
                    // Build the new User object. The profilePictureUrl is null if no image is selected.
                    val newUser = User.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .uid(userId)
                        .profilePictureUrl(null)
                        .friends(null)
                        .programs(null)
                        .build()

                    firestoreManager.saveUser(userId, newUser, object : FirebaseManager.FirebaseCallback {
                        override fun onSuccess() {
                            // If an image was selected, upload it; otherwise, continue directly
                            if (fileUri != null) {
                                firestoreManager.uploadProfilePicture(userId, fileUri!!, object : FirebaseManager.FirebaseCallback {
                                    override fun onSuccess() {
                                        Toast.makeText(this@RegisterActivity, "Registration + Photo successful!", Toast.LENGTH_SHORT).show()
                                        navigateToMain()
                                    }
                                    override fun onFailure(errorMessage: String) {
                                        Toast.makeText(this@RegisterActivity, "Failed to upload photo: $errorMessage", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            } else {
                                Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                                navigateToMain()
                            }
                        }
                        override fun onFailure(errorMessage: String) {
                            Toast.makeText(this@RegisterActivity, "Failed to save user data", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            override fun onFailure(errorMessage: String) {
                Toast.makeText(this@RegisterActivity, "Registration failed: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Navigate to the MainActivity after successful registration
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
