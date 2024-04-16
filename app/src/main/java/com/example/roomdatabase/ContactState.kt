package com.example.roomdatabase

data class ContactState(
    val firstName :String = "",
    val lastName : String ="",
    val phoneNumber: String ="",
    val contact: List<Contact> = emptyList(),
    val isAddingContact : Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME


)
