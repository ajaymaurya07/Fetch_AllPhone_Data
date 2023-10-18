package com.example.contact.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contact.fragement.CallFragment
import com.example.contact.fragement.ContactFragment
import com.example.contact.FragementActivity
import com.example.contact.fragement.MessageFragment

class ViewPagerAdapter(fragementActivity: FragementActivity):FragmentStateAdapter(fragementActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> ContactFragment()
            1-> MessageFragment()
            else-> CallFragment()
        }
    }
}