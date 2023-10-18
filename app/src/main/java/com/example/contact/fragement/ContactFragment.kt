package com.example.contact.fragement


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.CallListner
import com.example.contact.FragementActivity
import com.example.contact.adapter.RecylerAdapter
import com.example.contact.databinding.FragmentContactBinding
import com.example.contact.viewmodel.MyViewModel


class ContactFragment : Fragment() {

    lateinit var binding : FragmentContactBinding
    lateinit var viewModel: MyViewModel
    lateinit var rvAdapter: RecylerAdapter
    lateinit var Mobile:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var context : Context = requireContext()
        binding = FragmentContactBinding.inflate(layoutInflater,container,false)
        binding.contactRecyler.layoutManager = LinearLayoutManager(context)

        viewModel = (activity as FragementActivity).viewModel

        if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_CALL_LOG),1002)
        }else{
            getCallLogs()
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1002 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getCallLogs()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_CALL_LOG),1002)

        }
        if (requestCode==1004 && grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),1004)
        }else{
            callIntent(Mobile)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getCallLogs(){
        viewModel.contactalldata.observe(viewLifecycleOwner, Observer {
            rvAdapter = RecylerAdapter(it,object : CallListner {
                override fun onCallBtnClick(mob: String) {
                    makeCall(mob)
                    Mobile = mob
                }

            })
            binding.contactRecyler.adapter = rvAdapter
            rvAdapter.notifyDataSetChanged()
        })
    }
    fun makeCall(mobile:String){
        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),1004)
        }else{

            callIntent(mobile)
        }



    }
    fun callIntent(mobile: String){
        var intentCall = Intent(Intent.ACTION_CALL)
        intentCall.data = Uri.parse("tel:$mobile")
        startActivity(intentCall)
    }

}
