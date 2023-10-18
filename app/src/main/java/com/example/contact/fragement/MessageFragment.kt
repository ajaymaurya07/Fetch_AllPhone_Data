package com.example.contact.fragement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.FragementActivity
import com.example.contact.adapter.MessageAdapter
import com.example.contact.databinding.FragmentMessageBinding


class MessageFragment : Fragment() {
    lateinit var binding: FragmentMessageBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentMessageBinding.inflate(layoutInflater,container,false)

        (activity as FragementActivity).viewModel.messagealldata.observe(requireActivity(), Observer {
            var adapter=MessageAdapter(it)
            binding.rMessage.layoutManager=LinearLayoutManager(context)
            binding.rMessage.adapter=adapter

        })

        return binding.root
    }


}