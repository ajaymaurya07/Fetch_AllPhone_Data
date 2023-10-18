package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.CallListner
import com.example.contact.RandomColors
import com.example.contact.dataclass.Contact
import com.example.contact.databinding.ContactPrototypeBinding

class RecylerAdapter(private val contactList: List<Contact>,var onclick : CallListner):RecyclerView.Adapter<RecylerAdapter.ContactViewHolder>() {

    class ContactViewHolder(var binding:ContactPrototypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        var contact=ContactPrototypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactViewHolder(contact)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        var listItems  = contactList[position]
        holder.binding.tvName.text = listItems.name
        holder.binding.tvMob.text = listItems.phoneno
        holder.binding.ivContact.circleBackgroundColor = RandomColors().randomColors()
        holder.binding.btnCall.setOnClickListener {
            onclick.onCallBtnClick(listItems.phoneno)
        }
    }

    override fun getItemCount():Int{
        return  contactList.size
    }

}
