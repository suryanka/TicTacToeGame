package com.ghosh.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ghosh.tictactoeapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpBinding: ActivitySignUpBinding
    val auth= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding=ActivitySignUpBinding.inflate(layoutInflater)
        val view=signUpBinding.root
        setContentView(view)

        signUpBinding.buttonSignUpSignIn.setOnClickListener {
            val email = signUpBinding.EditTextEmailSignUp.text.toString()
            val password = signUpBinding.editTextPasswordSignUp.text.toString()

            signUpWithFirebase(email, password)
        }
    }

    fun signUpWithFirebase(email: String, password:String)
    {
        signUpBinding.ProgressBar.visibility=View.VISIBLE
        signUpBinding.buttonSignUpSignIn.isClickable=false
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                Toast.makeText(this@SignUpActivity,"Your account has been created.",Toast.LENGTH_LONG).show()
                finish()
                signUpBinding.ProgressBar.visibility=View.GONE
                signUpBinding.buttonSignUpSignIn.isClickable=true


            }
            else
            {
                Toast.makeText(this@SignUpActivity,
                    task.exception?.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }
}