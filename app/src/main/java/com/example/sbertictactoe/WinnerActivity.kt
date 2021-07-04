package com.example.sbertictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WinnerActivity : AppCompatActivity() {

    private lateinit var tvWinner: TextView
    private lateinit var ivWinner: ImageView
    private lateinit var tvWinnerSpeech: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)


        val playerWin: Int = intent.getIntExtra("PlayerWin", 0)
        val computerWin: Int = intent.getIntExtra("ComputerWin", 0)

        val winner = if (playerWin > computerWin) {
            "Player Win"
        } else "Computer Win"


        // Capture the layout's TextView and set the string as its text
        tvWinner = findViewById<TextView>(R.id.tv_winner).apply {
            text = winner
        }
        tvWinnerSpeech = findViewById<TextView>(R.id.tv_winner_speech).apply {
            val winSpeech = if (playerWin > computerWin) {
                resources.getString(R.string.player_win_speech)
            } else resources.getString(R.string.computer_win_speech)
            text = winSpeech
        }
        ivWinner = findViewById<ImageView>(R.id.iv_winner).apply {
            val level = if (playerWin > computerWin) {
                1
            } else 0
            setImageLevel(level)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}