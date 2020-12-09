package com.example.clean.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clean.ListFragment

//Adapter for the tabs on the viewpager
class TabAdapter (manager: FragmentManager, lifecycle: Lifecycle, private val tabs: Array<String>):
    FragmentStateAdapter(manager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0, 1, 2, 3, 4, 5, 6, 7, 8 -> return ListFragment()
        }
        return ListFragment()
    }

    override fun getItemCount(): Int {
        return tabs.size
    }
}