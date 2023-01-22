package com.ghosh.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ghosh.tictactoeapp.databinding.ActivityTictactoeBinding

var singleUser: Boolean =false
class TicktacktoeActivity : AppCompatActivity() {
    lateinit var ticktactoeBinding: ActivityTictactoeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ticktactoeBinding=ActivityTictactoeBinding.inflate(layoutInflater)
        val view=ticktactoeBinding.root
        setContentView(view)

        ticktactoeBinding.buttonSingle.setOnClickListener {
            singleUser=true
            val intent= Intent(this@TicktacktoeActivity,GamePlayActivity::class.java)
            startActivity(intent)
        }
        ticktactoeBinding.buttonMulti.setOnClickListener {
            singleUser=false
            val intent= Intent(this@TicktacktoeActivity,GameSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}