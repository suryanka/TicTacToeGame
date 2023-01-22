package com.ghosh.tictactoeapp

import android.content.ContentProviderClient
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ghosh.tictactoeapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    val auth:FirebaseAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding=ActivityLoginBinding.inflate(layoutInflater)
        val view=loginBinding.root
        setContentView(view)

        val textOfGoogleSignIn= loginBinding.loginGoogleSignIn.getChildAt(0) as TextView
        textOfGoogleSignIn.text="Continue with google"
        textOfGoogleSignIn.setTextColor(Color.BLACK)
        textOfGoogleSignIn.textSize=18F

        registerActivityForGoogleSignin()


        loginBinding.LoginSignIn.setOnClickListener {

            val email=loginBinding.EditTextEmail.text.toString()
            val passWord=loginBinding.editTextPassword.text.toString()
            signInWithEmailAndPassword(email, passWord);

        }

        loginBinding.loginGoogleSignIn.setOnClickListener {
            signInGoogle()
        }

        loginBinding.LoginForgot.setOnClickListener{
            val intent=Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        loginBinding.LoginSignUp.setOnClickListener{
            val intent=Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun signInWithEmailAndPassword(email:String, password: String)
    {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                Toast.makeText(this@LoginActivity,"Welcome to TicTacToe Game",Toast.LENGTH_LONG).show()
                val intent=Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this@LoginActivity,task.exception?.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user=auth.currentUser
        if(user!=null)
        {
            Toast.makeText(this@LoginActivity,"Welcome to TicTacToe Game",Toast.LENGTH_LONG).show()
            val intent=Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun registerActivityForGoogleSignin()
    {
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback { result->
            val resultCode=result.resultCode
            val data=result.data

            if(resultCode== RESULT_OK && data!=null)
            {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                firebaseSignInWithGoogle(task)
            }

        })
    }

    fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount>)
    {
        try{
            val account:GoogleSignInAccount=task.getResult(ApiException::class.java)
            Toast.makeText(this@LoginActivity,"Welcome to TicTacToe Game",Toast.LENGTH_LONG).show()
            val intent=Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
            firebaseSignInAccountWithGoogle(account)
        }
        catch(e:Exception)
        {
            Toast.makeText(this@LoginActivity,e.localizedMessage.toString(),Toast.LENGTH_LONG).show()

        }
    }

    fun firebaseSignInAccountWithGoogle(account:GoogleSignInAccount)
    {
        val authCredential=GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(authCredential).addOnCompleteListener { task->
            if(task.isSuccessful)
            {

            }
            else
            {

            }
        }
    }

    fun signInGoogle()
    {
        var gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("158803966325-60b3k3ltgamppdttc4t488j3ppng6ogv.apps.googleusercontent.com").requestEmail().build()

        googleSignInClient=GoogleSignIn.getClient(this, gso)
        signIn()
    }

    fun signIn()
    {
        var signInIntent:Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }
}