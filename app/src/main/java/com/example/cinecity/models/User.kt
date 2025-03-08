package com.example.cinecity.models


data class UserSearch(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val uid: String = "",
    val profilePictureUrl: String? = null
)

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val uid: String = "",
    val friends: List<String>? = null,
    val programs: List<Program>? = null,
    val profilePictureUrl: String? = null

) {
    class Builder {
        private var firstName: String = ""
        private var lastName: String = ""
        private var email: String = ""
        private var uid: String = ""
        private var friends: List<String>? = null
        private var programs: List<Program>? = null
        private var profilePictureUrl: String? = null

        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun email(email: String) = apply { this.email = email }
        fun uid(uid: String) = apply { this.uid = uid }
        fun friends(friends: List<String>?) = apply { this.friends = friends }
        fun programs(programs: List<Program>?) = apply { this.programs = programs }
        fun profilePictureUrl(profilePictureUrl: String?) = apply { this.profilePictureUrl = profilePictureUrl }

        fun build() = User(firstName, lastName, email, uid, friends, programs, profilePictureUrl)
    }
}
