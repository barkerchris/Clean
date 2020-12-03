package com.example.clean

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_preferences.*

class PreferencesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preferences, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articles = mutableSetOf<String>()
        val notifications = mutableSetOf<String>()

        val spinner = lst_countries
        context?.let {
            ArrayAdapter.createFromResource(it, R.array.countries, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
        }
        chk_general.setOnClickListener {
            if(chk_general.isChecked) {
                articles.add(chk_general.text.toString())
            } else {
                articles.remove(chk_general.text.toString())
            }
        }
        chk_general2.setOnClickListener {
            if(chk_general2.isChecked) {
                notifications.add(chk_general2.text.toString())
            } else {
                notifications.remove(chk_general2.text.toString())
            }
        }
        chk_technology.setOnClickListener {
            if(chk_technology.isChecked) {
                articles.add(chk_technology.text.toString())
            } else {
                articles.remove(chk_technology.text.toString())
            }
        }
        chk_technology2.setOnClickListener {
            if(chk_technology2.isChecked) {
                notifications.add(chk_technology2.text.toString())
            } else {
                notifications.remove(chk_technology2.text.toString())
            }
        }
        chk_science.setOnClickListener {
            if(chk_science.isChecked) {
                articles.add(chk_science.text.toString())
            } else {
                articles.remove(chk_science.text.toString())
            }
        }
        chk_science2.setOnClickListener {
            if(chk_science2.isChecked) {
                notifications.add(chk_science2.text.toString())
            } else {
                notifications.remove(chk_science2.text.toString())
            }
        }
        chk_sports.setOnClickListener {
            if(chk_sports.isChecked) {
                articles.add(chk_sports.text.toString())
            } else {
                articles.remove(chk_sports.text.toString())
            }
        }
        chk_sports2.setOnClickListener {
            if(chk_sports2.isChecked) {
                notifications.add(chk_sports2.text.toString())
            } else {
                notifications.remove(chk_sports2.text.toString())
            }
        }
        chk_business.setOnClickListener {
            if(chk_business.isChecked) {
                articles.add(chk_business.text.toString())
            } else {
                articles.remove(chk_business.text.toString())
            }
        }
        chk_business2.setOnClickListener {
            if(chk_business2.isChecked) {
                notifications.add(chk_business2.text.toString())
            } else {
                notifications.remove(chk_business2.text.toString())
            }
        }
        chk_entertainment.setOnClickListener {
            if(chk_entertainment.isChecked) {
                articles.add(chk_entertainment.text.toString())
            } else {
                articles.remove(chk_entertainment.text.toString())
            }
        }
        chk_entertainment2.setOnClickListener {
            if(chk_entertainment2.isChecked) {
                notifications.add(chk_entertainment2.text.toString())
            } else {
                notifications.remove(chk_entertainment2.text.toString())
            }
        }
        chk_health.setOnClickListener {
            if(chk_health.isChecked) {
                articles.add(chk_health.text.toString())
            } else {
                articles.remove(chk_health.text.toString())
            }
        }
        chk_health2.setOnClickListener {
            if(chk_health2.isChecked) {
                notifications.add(chk_health2.text.toString())
            } else {
                notifications.remove(chk_health2.text.toString())
            }
        }

        btn_save_articles.setOnClickListener {
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("articlePreferences", 0)
            val sharedPrefsEdit: SharedPreferences.Editor = sharedPrefs.edit()
            sharedPrefsEdit.putString("country", lst_countries.selectedItem.toString())
            sharedPrefsEdit.putStringSet("categories", articles)
            sharedPrefsEdit.apply()
        }
        btn_save_notifications.setOnClickListener {
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("notificationPreferences", 0)
            val sharedPrefsEdit: SharedPreferences.Editor = sharedPrefs.edit()
            sharedPrefsEdit.putStringSet("categories", notifications)
            sharedPrefsEdit.apply()
        }
    }

}