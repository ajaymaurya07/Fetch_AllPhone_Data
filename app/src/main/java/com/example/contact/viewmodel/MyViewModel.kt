package com.example.contact.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contact.dataclass.CalllogModel
import com.example.contact.dataclass.Contact
import com.example.contact.dataclass.MessageModel

class MyViewModel:ViewModel() {

    var contactalldata:MutableLiveData<MutableList<Contact>> = MutableLiveData()
    var messagealldata:MutableLiveData<MutableList<MessageModel>> = MutableLiveData()
    var calllogAlldata:MutableLiveData<MutableList<CalllogModel>> = MutableLiveData()

}