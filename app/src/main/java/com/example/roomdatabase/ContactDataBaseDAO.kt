package com.example.roomdatabase

import androidx.room.Database

@Database(
 entities = [Contact::class],
 version = 1
)
 abstract class ContactDataBaseDAO {
  abstract val dao : ContactDAO
}