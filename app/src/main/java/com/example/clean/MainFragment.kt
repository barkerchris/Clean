package com.example.clean

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clean.adapters.TabAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

//Main fragment hosts the viewpager alongside the tab layout
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabTitles = resources.getStringArray(R.array.tab_titles)
        val adapter = TabAdapter(parentFragmentManager, lifecycle, tabTitles)
        view_pager.adapter = adapter

        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("currentTab", 0)
                val sharedPrefsEdit: SharedPreferences.Editor = sharedPrefs.edit()
                sharedPrefsEdit.putInt("tabIndex", tab_layout.selectedTabPosition)
                sharedPrefsEdit.apply()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        TabLayoutMediator(tab_layout, view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = tabTitles[0]
                    1 -> tab.text = tabTitles[1]
                    2 -> tab.text = tabTitles[2]
                    3 -> tab.text = tabTitles[3]
                    4 -> tab.text = tabTitles[4]
                    5 -> tab.text = tabTitles[5]
                    6 -> tab.text = tabTitles[6]
                    7 -> tab.text = tabTitles[7]
                    8 -> tab.text = tabTitles[8]
                }
            }).attach()
    }

}