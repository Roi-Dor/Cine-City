package com.example.cinecity.utilities

import android.net.Uri
import android.util.Log
import com.example.cinecity.models.Program
import com.example.cinecity.models.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseManager private constructor() {

    // Reference to the Realtime Database, pointing to the "users" node
    private val dbRef = FirebaseDatabase.getInstance().getReference("users")
    private val storageRef: StorageReference =
        FirebaseStorage.getInstance().reference.child("profile_pictures")

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
        val userProgramsRef = dbRef.child(userId).child("programs")

        userProgramsRef.push().setValue(program)
            .addOnSuccessListener { callback.onSuccess() }
            .addOnFailureListener { e -> callback.onFailure(e.message ?: "Failed to add program") }
    }

    fun getUserPrograms(userId: String, callback: ProgramsCallback) {
        val userProgramsRef = dbRef.child(userId).child("programs")

        userProgramsRef.get()
            .addOnSuccessListener { dataSnapshot ->
                val programsList = mutableListOf<Program>()
                for (programSnapshot in dataSnapshot.children) {
                    val program = programSnapshot.getValue(Program::class.java)
                    if (program != null) {
                        programsList.add(program)
                    }
                }
                callback.onSuccess(programsList)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Failed to retrieve programs")
            }
    }

    fun uploadProfilePicture(userId: String, fileUri: Uri?, callback: FirebaseCallback) {
        if (fileUri == null) {
            Log.e("FirebaseStorage", "File URI is null, cannot upload!")
            callback.onFailure("File URI is null")
            return
        }

        val userPicRef = storageRef.child("$userId.jpg")
        Log.d("FirebaseStorage", "Uploading file to: ${userPicRef.path}")

        userPicRef.putFile(fileUri)
            .addOnSuccessListener {
                Log.d("FirebaseStorage", "Upload successful! Fetching download URL...")

                userPicRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    Log.d("FirebaseStorage", "Download URL: $downloadUri")

                    dbRef.child(userId).child("profilePictureUrl")
                        .setValue(downloadUri.toString())
                        .addOnSuccessListener {
                            Log.d("FirebaseStorage", "Profile picture URL saved in Realtime DB")
                            callback.onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirebaseStorage", "Failed to update DB: ${e.message}")
                            callback.onFailure(e.message ?: "Database update failed")
                        }
                }.addOnFailureListener { e ->
                    Log.e("FirebaseStorage", "Failed to retrieve download URL: ${e.message}")
                    callback.onFailure(e.message ?: "Download URL fetch failed")
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseStorage", "Upload failed: ${e.message}")
                callback.onFailure(e.message ?: "Upload failed")
            }
    }


    fun searchUsers(query: String, callback: UsersCallback) {
        val searchQuery = dbRef.orderByChild("firstName")
            .startAt(query)
            .endAt(query + "\uf8ff")

        searchQuery.get()
            .addOnSuccessListener { snapshot ->
                val userList = mutableListOf<User>()
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                callback.onSuccess(userList)
            }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Search failed.")
            }
    }

    fun addFriend(currentUserId: String, friendId: String, callback: FirebaseCallback) {
        // Reference to current user's "friends" node.
        val friendRef = dbRef.child(currentUserId).child("friends")
        // Set the friendId as the key with value true.
        friendRef.child(friendId).setValue(true)
            .addOnSuccessListener { callback.onSuccess() }
            .addOnFailureListener { e ->
                callback.onFailure(e.message ?: "Failed to add friend")
            }
    }



    interface UsersCallback {
        fun onSuccess(users: List<User>)
        fun onFailure(errorMessage: String)
    }



    // Callback interface for fetching programs
    interface ProgramsCallback {
        fun onSuccess(programs: List<Program>)
        fun onFailure(errorMessage: String)
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
