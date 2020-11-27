package com.example.clean

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        //requireActivity().actionBar?.hide()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mAuth.currentUser != null) {
            signedIn(view)
        } else {
            signedOut(view)
        }

        view.findViewById<MaterialButton>(R.id.btn_signin).setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_signinFragment)
        }
        view.findViewById<MaterialButton>(R.id.btn_register).setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_registerFragment)
        }
        view.findViewById<MaterialButton>(R.id.btn_signout).setOnClickListener {
            mAuth.signOut()
            signedOut(view)
        }
        val spinner = view.findViewById<Spinner>(R.id.lst_countries)
        context?.let {
            ArrayAdapter.createFromResource(it, R.array.countries, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
        }
        val categories = mutableSetOf<String>()
        view.findViewById<CheckBox>(R.id.chk_general).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_general).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_general).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_general).text.toString())
            }
        }
        view.findViewById<CheckBox>(R.id.chk_technology).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_technology).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_technology).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_technology).text.toString())
            }
        }
        view.findViewById<CheckBox>(R.id.chk_science).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_science).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_science).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_science).text.toString())
            }
        }
        view.findViewById<CheckBox>(R.id.chk_sports).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_sports).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_sports).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_sports).text.toString())
            }
        }
        view.findViewById<CheckBox>(R.id.chk_business).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_business).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_business).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_business).text.toString())
            }
        }
        view.findViewById<CheckBox>(R.id.chk_entertainment).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_entertainment).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_entertainment).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_entertainment).text.toString())
            }
        }
        view.findViewById<CheckBox>(R.id.chk_health).setOnClickListener {
            if(view.findViewById<CheckBox>(R.id.chk_health).isChecked) {
                categories.add(view.findViewById<CheckBox>(R.id.chk_health).text.toString())
            } else {
                categories.remove(view.findViewById<CheckBox>(R.id.chk_health).text.toString())
            }
        }
        view.findViewById<MaterialButton>(R.id.btn_save).setOnClickListener {
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("accountPreferences", 0)
            val sharedPrefsEdit: SharedPreferences.Editor = sharedPrefs.edit()
            sharedPrefsEdit.putString("country", view.findViewById<Spinner>(R.id.lst_countries).selectedItem.toString())
            sharedPrefsEdit.putStringSet("categories", categories)
            sharedPrefsEdit.apply()
        }
        view.findViewById<MaterialButton>(R.id.btn_get).setOnClickListener {
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("accountPreferences", 0)
            view.findViewById<MaterialTextView>(R.id.textView).text =
                sharedPrefs.getStringSet("categories", mutableSetOf()).toString()
        }
    }

    private fun signedIn(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_signin).isVisible = false
        view.findViewById<MaterialButton>(R.id.btn_register).isVisible = false
        view.findViewById<MaterialButton>(R.id.btn_signout).isVisible = true
    }

    private fun signedOut(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_signin).isVisible = true
        view.findViewById<MaterialButton>(R.id.btn_register).isVisible = true
        view.findViewById<MaterialButton>(R.id.btn_signout).isVisible = false
    }

}