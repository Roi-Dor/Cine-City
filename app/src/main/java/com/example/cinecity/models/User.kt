 package com.example.cinecity.models

    data class User(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val uid: String = "",
        val friends: Map<String, Boolean>? = null, // Already updated
        val programs: Map<String, Program>? = null,  // Updated from List<Program> to Map<String, Program>
        val profilePictureUrl: String? = null
    ) {
        // Helper to get programs as a list if needed:
        fun getProgramList(): List<Program> {
            return programs?.values?.toList() ?: emptyList()
        }
        fun getFriendIds(): List<String> {
            return friends?.keys?.toList() ?: emptyList()
        }

        class Builder {
            private var firstName: String = ""
            private var lastName: String = ""
            private var email: String = ""
            private var uid: String = ""
            private var friends: Map<String, Boolean>? = null
            private var programs: Map<String, Program>? = null
            private var profilePictureUrl: String? = null

            fun firstName(firstName: String) = apply { this.firstName = firstName }
            fun lastName(lastName: String) = apply { this.lastName = lastName }
            fun email(email: String) = apply { this.email = email }
            fun uid(uid: String) = apply { this.uid = uid }
            fun friends(friends: Map<String, Boolean>?) = apply { this.friends = friends }
            fun programs(programs: Map<String, Program>?) = apply { this.programs = programs }
            fun profilePictureUrl(profilePictureUrl: String?) = apply { this.profilePictureUrl = profilePictureUrl }

            fun build() = User(firstName, lastName, email, uid, friends, programs, profilePictureUrl)
        }
    }
