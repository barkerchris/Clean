package com.example.clean

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*

//Used to navigate to various profile-specific places
class ProfileFragment : Fragment() {
    var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mAuth.currentUser != null) {
            signedIn()
        } else {
            signedOut()
        }

        btn_signin.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_signinFragment)
        }
        btn_register.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_registerFragment)
        }
        btn_signout.setOnClickListener {
            mAuth.signOut()
            val sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences("accountPreferences", 0)
            val sharedPrefsEdit: SharedPreferences.Editor = sharedPrefs.edit()
            sharedPrefsEdit.putString("country", null)
            sharedPrefsEdit.putStringSet("categories", null)
            sharedPrefsEdit.apply()
            signedOut()
        }
        btn_preferences.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_preferencesFragment)
        }
        btn_saved_articles.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_savedListFragment)
        }
    }

    private fun signedIn() {
        btn_signin.isVisible = false
        btn_register.isVisible = false
        btn_signout.isVisible = true
        btn_saved_articles.isVisible = true
        btn_preferences.isVisible = true
    }

    private fun signedOut() {
        btn_signin.isVisible = true
        btn_register.isVisible = true
        btn_signout.isVisible = false
        btn_saved_articles.isVisible = false
        btn_preferences.isVisible = false
    }

}