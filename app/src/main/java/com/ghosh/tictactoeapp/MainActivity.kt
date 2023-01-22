package com.ghosh.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ghosh.tictactoeapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    val auth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding=ActivityMainBinding.inflate(layoutInflater)
        val view=mainBinding.root
        setContentView(view)

        mainBinding.buttonStartTTT.setOnClickListener {
            val intent=Intent(this@MainActivity,TicktacktoeActivity::class.java)
            startActivity(intent)
        }

        mainBinding.ButtonSignOut.setOnClickListener {
            //Sign Out With Email
            auth.signOut()

            //Sign Our from Google
            val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build()
            val googleSignInClient=GoogleSignIn.getClient(this@MainActivity,gso)
            googleSignInClient.signOut().addOnCompleteListener { task->
                if(task.isSuccessful)
                {
                    Toast.makeText(this@MainActivity,"SignOut is Successful.",Toast.LENGTH_LONG).show()
                }
            }

            val intent=Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}