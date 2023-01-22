package com.ghosh.tictactoeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ghosh.tictactoeapp.databinding.ActivityGamePlayBinding
import com.ghosh.tictactoeapp.databinding.ActivityGameSelectionBinding

class GameSelectionActivity : AppCompatActivity() {
    lateinit var gameSelectionBinding: ActivityGameSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameSelectionBinding=ActivityGameSelectionBinding.inflate(layoutInflater)
        val view=gameSelectionBinding.root
        setContentView(view)

        gameSelectionBinding.buttonOffline.setOnClickListener {
            singleUser=false
            val intent= Intent(this@GameSelectionActivity,GamePlayActivity::class.java)
            startActivity(intent)
        }

        gameSelectionBinding.buttonOnline.setOnClickListener {
            singleUser=false
            val intent= Intent(this@GameSelectionActivity,OnlineCodeGeneratorActivity::class.java)
            startActivity(intent)
        }
    }
}