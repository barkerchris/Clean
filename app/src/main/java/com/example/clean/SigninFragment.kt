package com.example.clean

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SigninFragment : Fragment() {
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        activity?.actionBar?.hide()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailText = view.findViewById<TextInputLayout>(R.id.edt_email).editText!!.text.toString()
        val passwordText = view.findViewById<TextInputLayout>(R.id.edt_password).editText!!.text.toString()
        view.findViewById<MaterialButton>(R.id.btn_signin).setOnClickListener {
            try {
                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Snackbar.make(view, resources.getString(R.string.msg_signedin), Snackbar.LENGTH_LONG).show()
                        view.findNavController().navigate(R.id.action_signinFragment_to_profileFragment)
                    } else {
                        Snackbar.make(view, resources.getString(R.string.err_signin), Snackbar.LENGTH_LONG).show()
                    }

                })
            } catch (exc: Throwable) {
                Snackbar.make(view, resources.getString(R.string.err_empty), Snackbar.LENGTH_LONG).show()
            }
        }
    }

}