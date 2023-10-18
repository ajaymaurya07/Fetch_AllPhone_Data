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
import com.example.contact.adapter.CallLogAdapter
import com.example.contact.adapter.RecylerAdapter
import com.example.contact.databinding.FragmentCallBinding


class CallFragment : Fragment() {
    lateinit var binding:FragmentCallBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentCallBinding.inflate(layoutInflater,container,false)

        (activity as FragementActivity).viewModel.calllogAlldata.observe(requireActivity(), Observer {
            val adapter = CallLogAdapter(it)
            binding.calllogRecyler.layoutManager=LinearLayoutManager(context)
            binding.calllogRecyler.adapter=adapter
        })

        return binding.root
    }


}