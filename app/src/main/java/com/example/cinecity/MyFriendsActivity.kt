package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.MyFriendsAdapter
import com.example.cinecity.databinding.ActivityMyFriendsBinding
import com.example.cinecity.fragments.BottomBarFragment
import com.example.cinecity.models.Friend

class MyFriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sample data: you would normally fetch this from a database or server
        val friendsList = listOf(
            Friend("https://www.imdb.com/name/nm1676221/mediaviewer/rm1350209538/?ref_=ext_shr_lnk4", "Andy"),
            Friend("https://www.imdb.com/name/nm2024927/mediaviewer/rm219170561/?ref_=ext_shr_lnk", "Alen"),
            Friend("https://www.imdb.com/name/nm1275259/mediaviewer/rm2713069056/?ref_=ext_shr_lnk", "Alexandra"),
            // Add as many as needed...
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
