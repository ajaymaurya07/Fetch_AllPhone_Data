package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.R
import com.example.contact.databinding.CalllogPrototypeBinding
import com.example.contact.databinding.MessagePrototypeBinding
import com.example.contact.dataclass.CalllogModel
import com.example.contact.dataclass.MessageModel

class CallLogAdapter(private val message: List<CalllogModel>): RecyclerView.Adapter<CallLogAdapter.ContactViewHolder>() {

    class ContactViewHolder(var binding: CalllogPrototypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        var contact= CalllogPrototypeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactViewHolder(contact)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        var callDetails = message[position]
        var duration = ""
        var durationSecond = 0
        if (callDetails.duration.toInt()<= 59){
            duration = "0:${callDetails.duration}"
        }else
        {
            var durationminute = callDetails.duration.toInt()/60
            durationSecond = callDetails.duration.toInt()%60
            duration = "$durationminute:$durationSecond"
        }
        holder.binding.mobNo.text = callDetails.number
        holder.binding.callDuration.text = "$duration"
//        holder.binding.callType.text = callDetails.callType
//        holder.binding.tvDate.text = callDetails.date.toString()

        var calltype = callDetails.type

        when(calltype){
            1 ->{
                holder.binding.callType.setImageResource(R.drawable.baseline_incoming_24)
            }
            2 ->{
                holder.binding.callType.setImageResource(R.drawable.baseline_call_made_24)
            }
            3 -> {
                holder.binding.callType.setImageResource(R.drawable.baseline_call_missed_24)
            }
            5 ->{
                holder.binding.callType.setImageResource(R.drawable.baseline_call_end_24)
            }
            6 ->{
                holder.binding.callType.setImageResource(R.drawable.baseline_block_24)
            }
        }
    }

    override fun getItemCount():Int{
        return  message.size
    }

}