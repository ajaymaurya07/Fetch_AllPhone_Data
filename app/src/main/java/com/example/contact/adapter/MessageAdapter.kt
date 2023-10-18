package com.example.contact.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.databinding.MessagePrototypeBinding
import com.example.contact.dataclass.MessageModel

class MessageAdapter(private val message: List<MessageModel>):RecyclerView.Adapter<MessageAdapter.ContactViewHolder>() {

    class ContactViewHolder(var binding:MessagePrototypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        var contact=MessagePrototypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactViewHolder(contact)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = message[position]
        holder.binding.senderName.text=item.address
        holder.binding.sendMessage.text=item.body

    }

    override fun getItemCount():Int{
        return  message.size
    }

}
