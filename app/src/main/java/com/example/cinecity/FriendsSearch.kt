package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.MyFriendsAdapter
import com.example.cinecity.databinding.ActivityFriendsSearchBinding
import com.example.cinecity.models.Friend
import com.example.cinecity.models.User
import com.example.cinecity.utilities.FirebaseManager

class FriendsSearch : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsSearchBinding
    private lateinit var friendsAdapter: MyFriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate back to MainActivity if appLogo clicked.
        binding.appLogo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Setup RecyclerView with an initially empty list.
        friendsAdapter = MyFriendsAdapter(emptyList())
        binding.friendsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.friendsRecyclerView.adapter = friendsAdapter

        // Listen for text changes to perform search.
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    FirebaseManager.getInstance().searchUsers(query, object : FirebaseManager.UsersCallback {
                        override fun onSuccess(users: List<User>) {
                            val friendsList = users.map { user ->
                                Friend(
                                    profilePicUrl = user.profilePictureUrl ?: "",
                                    userName = "${user.firstName} ${user.lastName}",
                                    uid = user.uid
                                )
                            }
                            friendsAdapter.updateList(friendsList)
                        }
                        override fun onFailure(errorMessage: String) {
                            Toast.makeText(this@FriendsSearch, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    })

                } else {
                    // Optionally, clear the adapter if query is empty.
                    friendsAdapter.updateList(emptyList())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }
}
