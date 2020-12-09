package com.example.clean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

//Register the user
class RegisterFragment : Fragment() {
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mAuth.currentUser != null) {
            view.findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        btn_register.setOnClickListener {
            register(view)
        }
    }

    private fun register(view: View) {
        val emailText = edt_email.editText!!.text.toString()
        val passwordText = edt_password.editText!!.text.toString()
        try {
            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(view, resources.getString(R.string.msg_registered), Snackbar.LENGTH_LONG).show()
                        view.findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
                    } else {
                        Snackbar.make(view, resources.getString(R.string.err_register), Snackbar.LENGTH_SHORT).show()
                    }
                })
        } catch (exc: Throwable) {
            Snackbar.make(view, resources.getString(R.string.err_empty), Snackbar.LENGTH_LONG).show()
        }
    }

}