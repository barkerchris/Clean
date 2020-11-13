package com.example.clean.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clean.ListFragment

class TabAdapter (activity: AppCompatActivity, private val tabs: Array<String>) :FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ListFragment()
        }
        return ListFragment()
    }

    override fun getItemCount(): Int {
        return tabs.size
    }
}