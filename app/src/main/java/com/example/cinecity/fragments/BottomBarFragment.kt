package com.example.cinecity.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.cinecity.FriendsSearch
import com.example.cinecity.MyFriendsActivity
import com.example.cinecity.R
import com.example.cinecity.UserProfileActivity

class BottomBarFragment : Fragment() {

    // Inflate the fragment_bottom_bar layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_bar, container, false)
    }

    // Once the view is created, find your views and set click listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgFriends = view.findViewById<ImageView>(R.id.bottom_bar_IMG_friends)
        val imgAdd = view.findViewById<ImageView>(R.id.bottom_bar_IMG_add)
        val imgProfile = view.findViewById<ImageView>(R.id.bottom_bar_IMG_profile)

        imgFriends.setOnClickListener {
            val intent = Intent(requireContext(), MyFriendsActivity::class.java)
            startActivity(intent)
        }

        imgAdd.setOnClickListener {
            // TODO: Add a new item, open camera, open a new activity, etc.
        }

        imgProfile.setOnClickListener {
            // Use requireContext() to get a valid context for the intent.
            val intent = Intent(requireContext(), UserProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
