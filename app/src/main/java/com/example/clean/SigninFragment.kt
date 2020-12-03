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
import kotlinx.android.synthetic.main.fragment_signin.*

class SigninFragment : Fragment() {
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(mAuth.currentUser != null) {
            view.findNavController().navigate(R.id.action_signinFragment_to_mainFragment)
        }

        btn_signin.setOnClickListener {
            login(view)
        }
    }

    private fun login(view: View) {
        val emailText = edt_email.editText!!.text.toString()
        val passwordText = edt_password.editText!!.text.toString()
        try {
            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Snackbar.make(view, resources.getString(R.string.msg_signedin), Snackbar.LENGTH_LONG).show()
                        view.findNavController().navigate(R.id.action_signinFragment_to_profileFragment)
                    } else {
                        Snackbar.make(view, resources.getString(R.string.err_signin), Snackbar.LENGTH_SHORT).show()
                    }
                })
        } catch (exc: Throwable) {
            Snackbar.make(view, resources.getString(R.string.err_empty), Snackbar.LENGTH_LONG).show()
        }
    }

}