package com.example.cinecity.utilities

object TimeFormatter {
    fun formatTime(lengthInMinutes: Int): String {
        val hours = lengthInMinutes / 60
        val minutes = lengthInMinutes % 60
        return buildString {
            append(String.format("%02dh", hours))
            append(" ")
            append(String.format("%02dm", minutes))
        }
    }
}
