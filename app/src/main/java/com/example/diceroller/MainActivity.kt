package com.example.dicegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diceroller.R
import java.util.Random

class MainActivity : AppCompatActivity() {
    private var tvResult: TextView? = null
    private var tvPlayer1: TextView? = null
    private var tvPlayer2: TextView? = null
    private var ivPlayer1: ImageView? = null
    private var ivPlayer2: ImageView? = null
    private var btnRoll: Button? = null

    private var player1Score = 0
    private var player2Score = 0
    private var round = 0
    private var player1Turn = false
    private var random: Random? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvPlayer1 = findViewById(R.id.tvPlayer1)
        tvPlayer2 = findViewById(R.id.tvPlayer2)
        ivPlayer1 = findViewById(R.id.ivPlayer1)
        ivPlayer2 = findViewById(R.id.ivPlayer2)
        btnRoll = findViewById(R.id.btnRoll)

        player1Score = 0
        player2Score = 0
        round = 0
        player1Turn = true
        random = Random()

        btnRoll!!.setOnClickListener {
            val player1Dice = rollDice(ivPlayer1)
            val player2Dice = rollDice(ivPlayer2)

            round++

            checkResult(player1Dice, player2Dice)

            checkEnd()
        }
    }

    private fun checkEnd() {
        TODO("Not yet implemented")
    }

    private fun rollDice(ivDice: ImageView?): Int {
        val diceValue = random!!.nextInt(6) + 1
        when (diceValue) {
            1 -> ivDice!!.setImageResource(R.drawable.dice_1)
            2 -> ivDice!!.setImageResource(R.drawable.dice_2)
            3 -> ivDice!!.setImageResource(R.drawable.dice_3)
            4 -> ivDice!!.setImageResource(R.drawable.dice_4)
            5 -> ivDice!!.setImageResource(R.drawable.dice_5)
            6 -> ivDice!!.setImageResource(R.drawable.dice_6)
        }

        return diceValue
    }

    private fun checkResult(player1Dice: Int, player2Dice: Int) {
        val sum = player1Dice + player2Dice

        if (player1Turn) {
            if (sum > 15) {
                player1Score++
                tvResult!!.text = "Player 1 wins the round!"
            } else {
                player2Score++
                tvResult!!.text = "Player 2 wins the round!"
            }
        } else {
            if (sum < 15) {
                player2Score++
                tvResult!!.text = "Player 2 wins the round!"
            } else {
                player1Score++
                tvResult!!.text = "Player 1 wins the round!"
            }
        }

        tvPlayer1!!.text = "Player 1: $player1Score"
        tvPlayer2!!.text = "Player 2: $player2Score"

        player1Turn = !player1Turn
        fun restartGame() {
            player1Score = 0
            player2Score = 0
            round = 0

            player1Turn = true

            ivPlayer1!!.setImageResource(R.drawable.dice_1)
            ivPlayer2!!.setImageResource(R.drawable.dice_1)

            tvResult!!.text = "Let's Play!"
            tvPlayer1!!.text = "Player 1: 0"
            tvPlayer2!!.text = "Player 2: 0"

            btnRoll!!.isEnabled = true
        }

        fun checkEnd() {
            if (round == 5) {
                btnRoll!!.isEnabled = false

                when {
                    player1Score > player2Score -> {
                        tvResult!!.text = "Player 1 wins the game!"
                    }

                    player1Score < player2Score -> {
                        tvResult!!.text = "Player 2 wins the game!"
                    }

                    else -> {
                        tvResult!!.text = "The game is a tie!"
                    }
                }

                Toast.makeText(this, "Tap to restart the game", Toast.LENGTH_LONG).show()

                findViewById<View>(android.R.id.content).setOnClickListener {
                    restartGame()
                }
            }
        }
    }
}
