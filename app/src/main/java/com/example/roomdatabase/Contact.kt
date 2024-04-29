package com.example.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val firstName: String,
    val secondName: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0
)
