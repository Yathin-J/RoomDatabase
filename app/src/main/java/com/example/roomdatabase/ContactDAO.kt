package com.example.roomdatabase

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface ContactDAO {
    @Upsert
    fun contactInsert(contact: Contact)
    @Delete
    fun contactDelete(contact: Contact)
    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactFromFirstName():Flow<List<Contact>>
    @Query("SELECT * FROM contact ORDER BY secondName ASC")
    fun getContactFromSecondName():Flow<List<Contact>>
    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun getContactFromPhoneNumber():Flow<List<Contact>>
}