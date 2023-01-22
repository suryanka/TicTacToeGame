package com.ghosh.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.ghosh.tictactoeapp.databinding.ActivityOnlineCodeGeneratorBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

var isCodeMaker =true
var code ="null"
var codeFound=false
var checktemp=true
lateinit var keyValue:String
class OnlineCodeGeneratorActivity : AppCompatActivity() {
    lateinit var onlineCodeGeneratorBinding: ActivityOnlineCodeGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onlineCodeGeneratorBinding=ActivityOnlineCodeGeneratorBinding.inflate(layoutInflater)
        val view=onlineCodeGeneratorBinding.root
        setContentView(view)

        onlineCodeGeneratorBinding.buttonCreate.setOnClickListener {
            code="null"
            codeFound= false
            checktemp=true
            keyValue="null"
            code=onlineCodeGeneratorBinding.EditTextCode.text.toString()
            onlineCodeGeneratorBinding.buttonCreate.visibility=View.GONE
            onlineCodeGeneratorBinding.buttonJoin.visibility=View.GONE
            onlineCodeGeneratorBinding.textViewhead.visibility=View.GONE
            onlineCodeGeneratorBinding.EditTextCode.visibility=View.GONE
            onlineCodeGeneratorBinding.progressBar.visibility=View.VISIBLE

            if(code!="null" && code!="")
            {
                isCodeMaker=true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var check=isValueAvailable(snapshot,code)
                        Handler().postDelayed({
                            if(check==true)
                            {
                                onlineCodeGeneratorBinding.buttonCreate.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.buttonJoin.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.textViewhead.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.EditTextCode.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.progressBar.visibility=View.GONE
                                Toast.makeText(this@OnlineCodeGeneratorActivity,"Please enter a unique code",Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
                                isValueAvailable(snapshot,code)
                                checktemp=false
                                Handler().postDelayed({
                                    accepted()
                                    Toast.makeText(this@OnlineCodeGeneratorActivity,"Please don't go back.",Toast.LENGTH_SHORT).show()
                                },
                                300)
                            }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else
            {
                onlineCodeGeneratorBinding.buttonCreate.visibility=View.VISIBLE
                onlineCodeGeneratorBinding.buttonJoin.visibility=View.VISIBLE
                onlineCodeGeneratorBinding.textViewhead.visibility=View.VISIBLE
                onlineCodeGeneratorBinding.EditTextCode.visibility=View.VISIBLE
                onlineCodeGeneratorBinding.progressBar.visibility=View.GONE
                Toast.makeText(this@OnlineCodeGeneratorActivity,"Please enter a valid code",Toast.LENGTH_SHORT).show()
            }
        }
        onlineCodeGeneratorBinding.buttonJoin.setOnClickListener {
            code="null"
            codeFound=false
            checktemp=true
            keyValue="null"
            code=onlineCodeGeneratorBinding.EditTextCode.text.toString()
            if(code!="null" && code!="")
            {
                code=onlineCodeGeneratorBinding.EditTextCode.text.toString()
                onlineCodeGeneratorBinding.buttonCreate.visibility=View.GONE
                onlineCodeGeneratorBinding.buttonJoin.visibility=View.GONE
                onlineCodeGeneratorBinding.textViewhead.visibility=View.GONE
                onlineCodeGeneratorBinding.EditTextCode.visibility=View.GONE
                onlineCodeGeneratorBinding.progressBar.visibility=View.VISIBLE

                isCodeMaker=false
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data: Boolean=isValueAvailable(snapshot, code)
                        Handler().postDelayed({
                            if(data==true){
                                codeFound=true
                                accepted()
                                onlineCodeGeneratorBinding.buttonCreate.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.buttonJoin.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.textViewhead.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.EditTextCode.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.progressBar.visibility=View.GONE
                            }else{
                                onlineCodeGeneratorBinding.buttonCreate.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.buttonJoin.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.textViewhead.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.EditTextCode.visibility=View.VISIBLE
                                onlineCodeGeneratorBinding.progressBar.visibility=View.GONE
                                Toast.makeText(this@OnlineCodeGeneratorActivity,"Invalid code Entered", Toast.LENGTH_SHORT).show()
                            }},2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }
            else
            {
                Toast.makeText(this@OnlineCodeGeneratorActivity,"Please enter a valid code", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun accepted()
    {
        var intent= Intent(this@OnlineCodeGeneratorActivity,OnlineMultiPlayerGamePlayActivity::class.java)
        startActivity(intent)
        onlineCodeGeneratorBinding.progressBar.visibility=View.GONE
        onlineCodeGeneratorBinding.buttonCreate.visibility=View.VISIBLE
        onlineCodeGeneratorBinding.buttonJoin.visibility=View.VISIBLE
        onlineCodeGeneratorBinding.EditTextCode.visibility=View.VISIBLE
        onlineCodeGeneratorBinding.textViewhead.visibility=View.VISIBLE

    }
    fun isValueAvailable(snapshot: DataSnapshot, code:String):Boolean
    {
        var data= snapshot.children
        data.forEach{
            var value=it.getValue().toString()
            if(value==code)
            {
                keyValue=it.key.toString()
                return true
            }
        }
        return false
    }
}