package com.deepak.myfirstkotlinapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

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
    internal var timeLeftOnTimer: Long = 10000

    companion object {
        private val SCORE_KEY = "SCORE KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)
        tapMe = findViewById(R.id.tapMeBtn)
        resetGame = findViewById(R.id.resetGame)
        currentScr = findViewById(R.id.currentScore)
        timeRemaining = findViewById(R.id.timeLeft)
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }

        tapMe.setOnClickListener {
            currentScr.text = incrementScore()
        }
        resetGame.setOnClickListener {
            resetGame()
        }
    }

    private fun restoreGame() {
        currentScr.text = "Your Current Score : $score"
        val timeLeft = timeLeftOnTimer / 1000
        timeRemaining.text = "Time Left : $timeLeft"
        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownValue) {
            override fun onTick(milisecUnitFinished: Long) {
                timeLeftOnTimer = milisecUnitFinished
                val timeLeft = milisecUnitFinished / 1000
                timeRemaining.text = "Time Left : $timeLeft"
            }

            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStart = true

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            SCORE_KEY,
            currentScr.text.toString().removePrefix("Your Current Score : ").toInt()
        )
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()

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
                timeLeftOnTimer = milisecUnitFinished
                val timeLeft = milisecUnitFinished / 1000
                timeRemaining.text = "Time Left : $timeLeft"
            }

            override fun onFinish() {
                endGame()
            }

        }
        gameStart = false
    }

    private fun endGame() {
        Toast.makeText(
            context,
            "Your Final Score : ${currentScr.text.removePrefix("Your Current Score : ")}",
            Toast.LENGTH_LONG
        )
            .show()
        resetGame()
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

    private fun showDialog() {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("Close Time Attack")
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            finish()
        }
        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    override fun onBackPressed() {

        showDialog()
    //    super.onBackPressed()
    }
}

