package com.example.cinecity.utilities

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager private constructor(context: Context) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    companion object {
        @Volatile
        private var instance: AuthManager? = null

        fun getInstance(context: Context): AuthManager {
            return instance ?: synchronized(this) {
                instance ?: AuthManager(context.applicationContext).also { instance = it }
            }
        }
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null

    fun getCurrentUserUid(): String? = firebaseAuth.currentUser?.uid

    fun loginUser(email: String, password: String, callback: AuthCallback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(firebaseAuth.currentUser)
                } else {
                    callback.onFailure(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun createUser(email: String, password: String, callback: AuthCallback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onSuccess(firebaseAuth.currentUser)
                } else {
                    callback.onFailure(task.exception?.message ?: "Registration failed")
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    interface AuthCallback {
        fun onSuccess(currentUser: FirebaseUser?)
        fun onFailure(errorMessage: String)
    }
}
