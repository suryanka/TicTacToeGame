package com.ghosh.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ghosh.tictactoeapp.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding
    val auth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view= forgotPasswordBinding.root
        setContentView(view)

        forgotPasswordBinding.resetButton.setOnClickListener {
            val email=forgotPasswordBinding.EditTextForgotEmail.text.toString()
            auth.sendPasswordResetEmail(email).addOnCompleteListener {task->
                if(task.isSuccessful)
                {
                    Toast.makeText(this@ForgotPasswordActivity,"Password reset email sent successfully",
                        Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this@ForgotPasswordActivity, task.exception?.localizedMessage.toString(),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}