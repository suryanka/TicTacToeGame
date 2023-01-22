package com.ghosh.tictactoeapp

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ghosh.tictactoeapp.databinding.ActivityOnlineMultiPlayerGamePlayBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlin.system.exitProcess

var isMyMove= isCodeMaker
class OnlineMultiPlayerGamePlayActivity : AppCompatActivity() {
    lateinit var onlineMultiPlayerGamePlayBinding: ActivityOnlineMultiPlayerGamePlayBinding

    var player1Count = 0
    var player2Count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onlineMultiPlayerGamePlayBinding=ActivityOnlineMultiPlayerGamePlayBinding.inflate(layoutInflater)
        val view=onlineMultiPlayerGamePlayBinding.root
        setContentView(view)

        onlineMultiPlayerGamePlayBinding.idOnlineResetButton.setOnClickListener { reset() }

        FirebaseDatabase.getInstance().reference.child("data").child(code).addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var data= snapshot.value
                if(isMyMove==true)
                {
                    isMyMove=false
                    moveOnline(data.toString(), isMyMove)
                }
                else
                {
                    isMyMove=true
                    moveOnline(data.toString(), isMyMove)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                Toast.makeText(this@OnlineMultiPlayerGamePlayActivity,"Game Reset",Toast.LENGTH_SHORT).show()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun reset()
    {
        player1.clear()
        player2.clear()
        emptyCells.clear()

        activeUser=1

        for(i in 1..9)
        {
            var buttonSelected:Button
            buttonSelected=when(i)
            {
                1->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox1
                2->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox2
                3->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox3
                4->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox4
                5->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox5
                6->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox6
                7->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox7
                8->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox8
                9->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox9

                else -> {  onlineMultiPlayerGamePlayBinding.idOnlineBtnBox1
                }
            }
            buttonSelected.isEnabled=true
            buttonSelected.setBackgroundColor(Color.parseColor("#9C27B0"))
            buttonSelected.text=""
            onlineMultiPlayerGamePlayBinding.idOnlineTVPlayer1.text="Player 1: $player1Count"
            onlineMultiPlayerGamePlayBinding.idOnlineTVPlayer2.text="Player 2: $player2Count"
            isMyMove= isCodeMaker
            if(isCodeMaker)
            {
                FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
            }
        }
    }

    fun buttonDisable()
    {
        for(i in 1..9)
        {
            var buttonSelected:Button
            buttonSelected=when(i)
            {
                1->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox1
                2->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox2
                3->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox3
                4->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox4
                5->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox5
                6->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox6
                7->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox7
                8->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox8
                9->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox9

                else -> {  onlineMultiPlayerGamePlayBinding.idOnlineBtnBox1
                }
            }
            if(buttonSelected.isEnabled){
                buttonSelected.isEnabled=false
            }
        }
    }

    fun removeCode()
    {
        if(isCodeMaker)
        {
            FirebaseDatabase.getInstance().reference.child("codes").child(keyValue).removeValue()
        }
    }

    fun disableReset()
    {
        onlineMultiPlayerGamePlayBinding.idOnlineResetButton.isEnabled=false
        Handler().postDelayed(object :Runnable{
            override fun run() {
                onlineMultiPlayerGamePlayBinding.idOnlineResetButton.isEnabled=true
            }

        },2000)
    }

    fun updateDatabase(cellid: Int)
    {
        FirebaseDatabase.getInstance().reference.child("data").child(code).push().setValue(cellid)
    }

    fun moveOnline(data:String, move:Boolean)
    {
        val audio=MediaPlayer.create(this@OnlineMultiPlayerGamePlayActivity, R.raw.button_click_sound)
        if(move)
        {
            var buttonSelected:Button
            buttonSelected= when(data.toInt())
            {
                1->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox1
                2->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox2
                3->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox3
                4->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox4
                5->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox5
                6->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox6
                7->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox7
                8->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox8
                9->onlineMultiPlayerGamePlayBinding.idOnlineBtnBox9

                else -> {
                    onlineMultiPlayerGamePlayBinding.idOnlineBtnBox1
                }
            }

            buttonSelected.text="0"
            onlineMultiPlayerGamePlayBinding.TVTurn.text="Turn : Player 1"
            buttonSelected.setBackgroundColor(Color.parseColor("#FFFFFF"))
            buttonSelected.setTextColor(Color.parseColor("#FFDE03"))
            player2.add(data.toInt())
            emptyCells.add(data.toInt())
            audio.start()
            Handler().postDelayed({
                audio.release()
            },200)
            buttonSelected.isEnabled=false
            checkWinner()
        }
    }

    fun checkWinner():Int
    {
        val audio= MediaPlayer.create(this@OnlineMultiPlayerGamePlayActivity,R.raw.interface_click_sound )
        if((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(7)))
        {
            player1Count+=1
            buttonDisable()
            audio.start()
            disableReset()
            Handler().postDelayed(object: Runnable{
                override fun run() {
                    audio.release()
                }

            },4000)
            var build= AlertDialog.Builder(this@OnlineMultiPlayerGamePlayActivity)
            build.setTitle("Game Over")
            build.setMessage("You have won!"+"\n\n"+"Do you want to play again")
            build.setPositiveButton("Ok"){dialogWindow, position ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit"){dialogWindow, position->
                audio.release()
                removeCode()
                exitProcess(1)
            }
            Handler().postDelayed(object:Runnable{
                override fun run() {
                    build.show()
                }

            },2000)
            return 1
        }
        else if((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(7) && player2.contains(5) && player2.contains(3)))
        {
            player2Count+=1
            buttonDisable()
            audio.start()
            disableReset()
            Handler().postDelayed(object: Runnable{
                override fun run() {
                    audio.release()
                }

            },4000)
            var build= AlertDialog.Builder(this@OnlineMultiPlayerGamePlayActivity)
            build.setTitle("Game Over")
            build.setMessage("Opponent won!"+"\n\n"+"Do you want to play again")
            build.setPositiveButton("Ok"){dialogWindow, position ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit"){dialogWindow, position->
                audio.release()
                removeCode()
                exitProcess(1)
            }
            Handler().postDelayed(object:Runnable{
                override fun run() {
                    build.show()
                }

            },2000)
            return 1
        }
        else if(emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                4
            ) &&
            emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) && emptyCells.contains(
                8
            )
            && emptyCells.contains(9))
        {

            buttonDisable()
            audio.start()
            disableReset()
            var build= AlertDialog.Builder(this@OnlineMultiPlayerGamePlayActivity)
            build.setTitle("Game Over")
            build.setMessage("Game Draw!"+"\n\n"+"Do you want to play again")
            build.setPositiveButton("Ok"){dialogWindow, position ->
                reset()
                audio.release()
            }
            build.setNegativeButton("Exit"){dialogWindow, position->
                audio.release()
                removeCode()
                exitProcess(1)
            }
            build.show()
            return 1
        }
        return 0
    }

    fun playNow(buttonSelected: Button, currCell:Int)
    {
        val audio=MediaPlayer.create(this@OnlineMultiPlayerGamePlayActivity, R.raw.button_click_sound)
        buttonSelected.text="X"
        onlineMultiPlayerGamePlayBinding.TVTurn.text="Turn : Player 2"
        buttonSelected.setBackgroundColor(Color.parseColor("#FFFFFF"))
        buttonSelected.setTextColor(Color.parseColor("#FFDE03"))
        player1.add(currCell)
        emptyCells.add(currCell)
        audio.start()
        buttonSelected.isEnabled=false
        Handler().postDelayed(object :Runnable{
            override fun run() {
                audio.release()
            }

        },200)
    }

    override fun onBackPressed() {
        removeCode()
        FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
        exitProcess(0)
    }

    fun buttonClick(view: View) {
        if(isMyMove)
        {
            val but=view as Button
            var cellOnline=0
            when(but.id)
            {
                R.id.idOnlineBtnBox1->cellOnline=1
                R.id.idOnlineBtnBox2->cellOnline=2
                R.id.idOnlineBtnBox3->cellOnline=3
                R.id.idOnlineBtnBox4->cellOnline=4
                R.id.idOnlineBtnBox5->cellOnline=5
                R.id.idOnlineBtnBox6->cellOnline=6
                R.id.idOnlineBtnBox7->cellOnline=7
                R.id.idOnlineBtnBox8->cellOnline=8
                R.id.idOnlineBtnBox9->cellOnline=9
            }
            playerTurn=false
            Handler().postDelayed(object:Runnable{
                override fun run() {
                    playerTurn=true
                }

            },600)
            playNow(but,cellOnline)
            updateDatabase(cellOnline)
        }
    }
}