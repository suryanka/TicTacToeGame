package com.ghosh.tictactoeapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.ghosh.tictactoeapp.databinding.ActivityGamePlayBinding

import kotlin.system.exitProcess

var playerTurn: Boolean =true
@SuppressLint("ParcelCreator")
class GamePlayActivity() : AppCompatActivity(), Parcelable {
    lateinit var gamePlayBinding: ActivityGamePlayBinding
    var player1Count = 0
    var player2Count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1

    constructor(parcel: Parcel) : this() {
        player1Count = parcel.readInt()
        player2Count = parcel.readInt()
        activeUser = parcel.readInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gamePlayBinding = ActivityGamePlayBinding.inflate(layoutInflater)
        val view = gamePlayBinding.root
        setContentView(view)

        gamePlayBinding.idResetButton.setOnClickListener {
            reset()
        }


//        fun buttonClick(view: View) {
//            if (playerTurn) {
//                val but: Button = view as Button
//                var cellId = 0
//                when (but.id) {
//                    R.id.idBtnBox1 -> cellId = 1
//                    R.id.idBtnBox2 -> cellId = 2
//                    R.id.idBtnBox3 -> cellId = 3
//                    R.id.idBtnBox4 -> cellId = 4
//                    R.id.idBtnBox5 -> cellId = 5
//                    R.id.idBtnBox6 -> cellId = 6
//                    R.id.idBtnBox7 -> cellId = 7
//                    R.id.idBtnBox8 -> cellId = 8
//                    R.id.idBtnBox9 -> cellId = 9
//                }
//                playerTurn = false
//                val handler = Handler(Looper.getMainLooper())
//                handler.postDelayed(object : Runnable {
//                    override fun run() {
//                        playerTurn = true
//                    }
//                }, 600)
//
//                playNow(but, cellId)
//            }
//        }
    }


        private fun CheckWinner(): Int {
            val audio = MediaPlayer.create(this, R.raw.interface_click_sound)
            if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
                (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
                (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
                (player1.contains(2) && player1.contains(5) && player1.contains(8)) ||
                (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
                (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
                (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
                (player1.contains(3) && player1.contains(5) && player1.contains(7))
            ) {
                player1Count += 1
                buttonDisable()
                audio.start()
                disableReset()
                val handler1 = Handler(Looper.getMainLooper())
                handler1.postDelayed(object : Runnable {
                    override fun run() {
                        audio.release()
                    }
                }, 4000)
                val alertDialog = AlertDialog.Builder(this@GamePlayActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Player 1 wins \n\n" + "Do you want to play again")
                alertDialog.setPositiveButton("Ok") { dialogWindow, position ->
                    reset()
                    audio.release()
                }
                alertDialog.setNegativeButton("Exit") { dialogWindow, position ->
                    audio.release()
                    exitProcess(1)
                }
                val handler2 = Handler(Looper.getMainLooper())
                handler2.postDelayed(object : Runnable {
                    override fun run() {
                        alertDialog.show()
                    }

                }, 2000)
                return 1
            } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
                (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
                (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
                (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
                (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
                (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
                (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
                (player2.contains(7) && player2.contains(5) && player2.contains(3))
            ) {
                player2Count += 1
                buttonDisable()
                disableReset()
                audio.start()
                val handler1 = Handler(Looper.getMainLooper())
                handler1.postDelayed(object : Runnable {
                    override fun run() {
                        audio.release()
                    }
                }, 4000)
                val alertDialog = AlertDialog.Builder(this@GamePlayActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Player 2 wins \n\n" + "Do you want to play again")
                alertDialog.setPositiveButton("Ok") { dialogWindow, position ->
                    reset()
                    audio.release()
                }
                alertDialog.setNegativeButton("Exit") { dialogWindow, position ->
                    audio.release()
                    exitProcess(1)
                }

                val handler2 = Handler(Looper.getMainLooper())
                handler2.postDelayed(object : Runnable {
                    override fun run() {
                        alertDialog.show()
                    }

                }, 2000)
                return 1
            } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                    4
                ) &&
                emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) && emptyCells.contains(
                    8
                )
                && emptyCells.contains(9)
            ) {
                val alertDialog = AlertDialog.Builder(this@GamePlayActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Game Draw \n\n" + "Do you want to play again")
                alertDialog.setPositiveButton("Ok") { dialogWindow, position ->
                    reset()
                }
                alertDialog.setNegativeButton("Exit") { dialogWindow, position ->
                    exitProcess(1)
                }

                alertDialog.show()
                return 1
            }
            return 0
        }

        fun playNow(buttonSelected: Button, currCell: Int) {
            val audio = MediaPlayer.create(this, R.raw.button_click_sound)
            if (activeUser == 1) {
                buttonSelected.text = "X"
                buttonSelected.setTextColor(Color.parseColor("#FF0266"))
                buttonSelected.setBackgroundColor(Color.parseColor("#FFFFFF"))
                player1.add(currCell)
                emptyCells.add(currCell)
                audio.start()
                buttonSelected.isEnabled = false
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        audio.release()
                    }

                }, 200)
                val checkwinner = CheckWinner()
                if (checkwinner == 1) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            reset()
                        }
                    }, 2000)
                } else if (singleUser) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            robot()
                        }
                    }, 500)
                } else {
                    activeUser = 0
                }
            } else {
                buttonSelected.text = "0"
                activeUser = 1
                player2.add(currCell)
                emptyCells.add(currCell)
                buttonSelected.setTextColor(Color.parseColor("#FF0266"))
                buttonSelected.setBackgroundColor(Color.parseColor("#FFFFFF"))
                audio.start()
                buttonSelected.isEnabled = false
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        audio.release()
                    }
                }, 200)
                val checkWinner = CheckWinner()
                if (checkWinner == 1) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            reset()
                        }
                    }, 4000)
                }

            }
        }


    private fun buttonDisable() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1

        for (i in 1..9) {
            var buttonSelected: Button
            buttonSelected = when (i) {
                1 -> gamePlayBinding.idBtnBox1
                2 -> gamePlayBinding.idBtnBox2
                3 -> gamePlayBinding.idBtnBox3
                4 -> gamePlayBinding.idBtnBox4
                5 -> gamePlayBinding.idBtnBox5
                6 -> gamePlayBinding.idBtnBox6
                7 -> gamePlayBinding.idBtnBox7
                8 -> gamePlayBinding.idBtnBox8
                9 -> gamePlayBinding.idBtnBox9
                else -> {
                    gamePlayBinding.idBtnBox1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.setBackgroundColor(Color.parseColor("#9C27B0"))
            buttonSelected.text = ""
            gamePlayBinding.idTVPlayer1.text = "Player 1: $player1Count"
            gamePlayBinding.idTVPlayer2.text = "Player 2: $player2Count"
        }
    }

    fun disableReset() {
        gamePlayBinding.idResetButton.isEnabled = false
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                gamePlayBinding.idResetButton.isEnabled = true
            }
        }, 2200)
    }


    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1

        for (i in 1..9) {
            var buttonSelected: Button
            buttonSelected = when (i) {
                1 -> gamePlayBinding.idBtnBox1
                2 -> gamePlayBinding.idBtnBox2
                3 -> gamePlayBinding.idBtnBox3
                4 -> gamePlayBinding.idBtnBox4
                5 -> gamePlayBinding.idBtnBox5
                6 -> gamePlayBinding.idBtnBox6
                7 -> gamePlayBinding.idBtnBox7
                8 -> gamePlayBinding.idBtnBox8
                9 -> gamePlayBinding.idBtnBox9
                else -> {
                    gamePlayBinding.idBtnBox1
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.setBackgroundColor(Color.parseColor("#9C27B0"))
            buttonSelected.text = ""
            gamePlayBinding.idTVPlayer1.text = "Player1: $player1Count"
            gamePlayBinding.idTVPlayer2.text = "Player2: $player2Count"
        }
    }


    private fun robot() {
        val rnd = (1..9).random()
        if (emptyCells.contains(rnd)) {
            robot()
        } else {
            val btnSelected: Button = when (rnd) {
                1 -> gamePlayBinding.idBtnBox1
                2 -> gamePlayBinding.idBtnBox2
                3 -> gamePlayBinding.idBtnBox3
                4 -> gamePlayBinding.idBtnBox4
                5 -> gamePlayBinding.idBtnBox5
                6 -> gamePlayBinding.idBtnBox6
                7 -> gamePlayBinding.idBtnBox7
                8 -> gamePlayBinding.idBtnBox8
                9 -> gamePlayBinding.idBtnBox9
                else -> gamePlayBinding.idBtnBox1
            }
            emptyCells.add(rnd)
            player2.add(rnd)
            val audio = MediaPlayer.create(
                this@GamePlayActivity,
                R.raw.button_click_sound
            )
            audio.start()
            btnSelected.setText("0")
            btnSelected.setBackgroundColor(Color.parseColor("#FFFFFF"))
            btnSelected.setTextColor(Color.parseColor("#FFDE03"))
            btnSelected.isEnabled = false
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object : Runnable {
                override fun run() {
                    audio.release()
                }
            }, 500)
            var checkWinner = CheckWinner()
            if (checkWinner == 1) {
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        reset()
                    }
                }, 2000)
            }
        }
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    fun buttonClick(view: View) {
        if (playerTurn) {
            val but: Button = view as Button
            var cellId = 0
            when (but.id) {
                R.id.idBtnBox1 -> cellId = 1
                R.id.idBtnBox2 -> cellId = 2
                R.id.idBtnBox3 -> cellId = 3
                R.id.idBtnBox4 -> cellId = 4
                R.id.idBtnBox5 -> cellId = 5
                R.id.idBtnBox6 -> cellId = 6
                R.id.idBtnBox7 -> cellId = 7
                R.id.idBtnBox8 -> cellId = 8
                R.id.idBtnBox9 -> cellId = 9
            }
            playerTurn = false
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object : Runnable {
                override fun run() {
                    playerTurn = true
                }
            }, 600)

            playNow(but, cellId)
        }
    }

}










