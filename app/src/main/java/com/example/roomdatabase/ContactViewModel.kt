package com.example.roomdatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    val contactDAO: ContactDAO
) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.FIRST_NAME -> contactDAO.getContactFromFirstName()
            SortType.LAST_NAME -> contactDAO.getContactFromSecondName()
            SortType.PHONE_NUMBER -> contactDAO.getContactFromPhoneNumber()

        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            contact = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun onEvent(event: ContactEvent) {
        when (event) {
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber

                if (firstName.isBlank() || lastName.isBlank()|| phoneNumber.isBlank()){
                    return
                }
                val contact =  Contact(
                    firstName = firstName,
                    secondName = lastName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch {

                }
            }

            is ContactEvent.DeleteContact ->
                viewModelScope.launch {
                    contactDAO.contactDelete(event.contact)
                }

            is ContactEvent.SetFirstName ->
                _state.update {
                    it.copy(
                        firstName = event.firstName
                    )
                }

            is ContactEvent.SetLastName ->
                _state.update {
                    it.copy(
                        lastName = event.secondName
                    )
                }

            is ContactEvent.SetPhoneNumber ->
                _state.update {
                    it.copy(
                        phoneNumber = event.phoneNumber
                    )
                }

            ContactEvent.ShowDialog ->
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }

            ContactEvent.HideDialog ->
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }

            is ContactEvent.SortType ->
                _sortType.value = event.sortType
        }
    }
}