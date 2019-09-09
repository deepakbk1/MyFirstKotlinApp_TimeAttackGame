package com.deepak.myfirstkotlinapp

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var tapMe: Button
    lateinit var resetGame: Button
    lateinit var currentScr: TextView
    lateinit var timeRemaining: TextView
    var score = 0
    lateinit var countDownTimer: CountDownTimer
    internal var initialTime: Long = 10000
    internal var countDownValue: Long = 1000
    lateinit var context: Context
    internal var gameStart = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)
        tapMe = findViewById(R.id.tapMeBtn)
        resetGame = findViewById(R.id.resetGame)

        currentScr = findViewById(R.id.currentScore)
        timeRemaining = findViewById(R.id.timeLeft)
        resetGame()
        tapMe.setOnClickListener {
            currentScr.text = incrementScore()
        }
        resetGame.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        score = 0
        initialTime = 10000
        countDownValue = 1000
        resetGame.isEnabled = false
        currentScr.text = "Your Current Score : $score"
        val timeLeft = initialTime / countDownValue
        timeRemaining.text = "Time Left : $timeLeft"
        countDownTimer = object : CountDownTimer(initialTime, countDownValue) {
            override fun onTick(milisecUnitFinished: Long) {
                val timeLeft = milisecUnitFinished / 1000
                timeRemaining.text = "Time Left : $timeLeft"
            }

            override fun onFinish() {
                Toast.makeText(
                    context,
                    "Your Final Score : ${currentScr.text.removePrefix("Your Current Score : ")}",
                    Toast.LENGTH_LONG
                )
                    .show()
                resetGame.isEnabled = true
                tapMe.isEnabled = false
            }

        }
        gameStart = false
    }

    private fun gameStart() {
        if (!gameStart) {
            countDownTimer.start()
            gameStart = true
        }
    }

    private fun incrementScore(): String {
        gameStart()
        score++
        return "Your Current Score : $score"

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}

