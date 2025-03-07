package com.example.cinecity.utilities

import com.example.cinecity.models.Program
import com.example.cinecity.models.User
import com.google.firebase.database.FirebaseDatabase

class FirebaseManager private constructor() {

    // Reference to the Realtime Database, pointing to the "users" node
    private val dbRef = FirebaseDatabase.getInstance().getReference("users")

    companion object {
        @Volatile
        private var instance: FirebaseManager? = null

        fun getInstance(): FirebaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseManager().also { instance = it }
            }
        }
    }

    fun saveUser(userId: String, user: User, callback: FirebaseCallback) {
        dbRef.child(userId).setValue(user)
            .addOnSuccessListener { callback.onSuccess() }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Failed to save user")
            }
    }

    fun getUser(userId: String, callback: UserCallback) {
        dbRef.child(userId).get()
            .addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    callback.onSuccess(user)
                } else {
                    callback.onFailure("User not found")
                }
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Error fetching user")
            }
    }

    fun addProgramToUser(userId: String, program: Program, callback: FirebaseCallback) {
        val userProgramsRef = FirebaseDatabase.getInstance().getReference("users")
            .child(userId)
            .child("programs")

        userProgramsRef.push().setValue(program)
            .addOnSuccessListener { callback.onSuccess() }
            .addOnFailureListener { e -> callback.onFailure(e.message ?: "Failed to add program") }
    }


    interface FirebaseCallback {
        fun onSuccess()
        fun onFailure(errorMessage: String)
    }

    interface UserCallback {
        fun onSuccess(user: User)
        fun onFailure(errorMessage: String)
    }
}
