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

    private fun initViews() {

        binding.registerBTNSubmit.setOnClickListener { registerUser() }
        binding.registerBTNBack.setOnClickListener { finish() }
        binding.uploadBTNImage.setOnClickListener { uploadImage() }
    }

    private fun uploadImage() {  // Fixed typo in function name
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Choose Profile Picture"), REQUEST_CODE_PICK_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data

            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
            } catch (e: Exception) {
                Log.e("Exception", "Error: ${e.localizedMessage}")
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 0 // Defined the request code
    }


    private fun registerUser() {
        val firstName = binding.registerETFirstName.editText?.text.toString().trim()
        val lastName = binding.registerETLastName.editText?.text.toString().trim()
        val email = binding.registerETEmail.editText?.text.toString().trim()
        val password = binding.registerETPassword.editText?.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (fileUri == null) {
            Log.e("FirebaseStorage", "File URI is null, cannot upload!")
            return
        }

        Log.d("FirebaseStorage", "Uploading file from URI: $fileUri")

        authManager.createUser(email, password, object : AuthManager.AuthCallback {
            override fun onSuccess(user: FirebaseUser?) {
                user?.uid?.let { userId ->
                    // Create a user object without a profile picture URL for now
                    val newUser = User.Builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .uid(userId)
                        .profilePictureUrl(null) // or a placeholder if you want
                        .friends(null)
                        .programs(null)
                        .build()

                    // Save basic user data to the database first
                    firestoreManager.saveUser(userId, newUser, object : FirebaseManager.FirebaseCallback {
                        override fun onSuccess() {
                            // If user saved successfully, now handle profile picture
                            if (fileUri != null) {
                                // We have a fileUri from user selection
                                firestoreManager.uploadProfilePicture(userId, fileUri!!, object : FirebaseManager.FirebaseCallback {
                                    override fun onSuccess() {
                                        // Picture uploaded & DB updated with final URL
                                        Toast.makeText(this@RegisterActivity, "Registration + Photo successful!", Toast.LENGTH_SHORT).show()
                                        navigateToMain()
                                    }
                                    override fun onFailure(errorMessage: String) {
                                        // Couldn’t upload the picture but user record is there
                                        Toast.makeText(this@RegisterActivity, "Failed to upload photo: $errorMessage", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            } else {
                                // No file was selected, just proceed
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


    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
