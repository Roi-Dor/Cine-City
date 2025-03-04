package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.MyFriendsAdapter
import com.example.cinecity.databinding.ActivityMyFriendsBinding
import com.example.cinecity.fragments.BottomBarFragment
import com.example.cinecity.models.Friend
import com.example.cinecity.utilities.ImageLoader

class MyFriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImageLoader.init(applicationContext)

        // Sample data: you would normally fetch this from a database or server
        val friendsList = listOf(
            Friend("https://image.tmdb.org/t/p/original/jMXU5oG3i93SH1yhkpbBGskFiJl.jpg", "Andy"),
            Friend("https://image.tmdb.org/t/p/original/wdmLUSPEC7dXuqnjTM4NgbjvTKk.jpg", "Alen"),
            Friend("https://image.tmdb.org/t/p/original/9f9nBQDrhpgh5jDuvCeZm1uUSZp.jpg", "Alexandra"),
            Friend("https://image.tmdb.org/t/p/original/g55dgcZQkLMolkKqgP7OD2yfGXu.jpg", "Gal"),
            Friend("https://image.tmdb.org/t/p/original/mf0OANvWYSzU1d8yggrhyw8IbIz.jpg", "Emma")
        )

        // Set up RecyclerView with MyFriendsAdapter
        val myFriendsAdapter = MyFriendsAdapter(friendsList)
        binding.myFriendsRVList.adapter = myFriendsAdapter
        binding.myFriendsRVList.layoutManager = LinearLayoutManager(this)


        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close FriendsSearch activity
        }


    }
}
