package com.example.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Contact(
    @PrimaryKey()
    val id :Int,
    val firstName : String,
    val secondName : String,
    val phoneNumber : String
)
