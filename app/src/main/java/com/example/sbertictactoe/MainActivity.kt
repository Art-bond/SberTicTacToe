package com.example.sbertictactoe

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<ImageButton>
    private lateinit var ivPlayer: ImageView
    private lateinit var ivComputer: ImageView
    private lateinit var btnReset: Button

    private var playerWin: Int = 0
    private var computerWin: Int = 0


    private var board = arrayListOf("", "", "", "", "", "", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivPlayer = findViewById(R.id.iv_player)
        ivComputer = findViewById(R.id.iv_computer)

        //init playing buttons
        buttons = Array(9) { pos ->
            initButton(pos)
        }

        btnReset = findViewById(R.id.btn_reset)


        btnReset.setOnClickListener {
            resetGame()
            Toast.makeText(applicationContext, "Reset button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetGame() {
        playerWin = 0
        computerWin = 0
        ivPlayer.drawable.level = 0
        ivComputer.drawable.level = 0
        refreshBoard()
    }

    private fun initButton(position: Int): ImageButton {
        val btn: ImageButton =
            findViewById(resources.getIdentifier("btn$position", "id", packageName))
        btn.setImageResource(0)
        btn.tag = ""
        btn.setOnClickListener {
            onBtnClick(btn, position)
        }
        return btn
    }

    private fun onBtnClick(btn: ImageButton, posPlayer: Int) {

        if (btn.tag == "") {
            btn.setImageResource(R.drawable.ic_cross_80)
            btn.tag = "X"
            board[posPlayer] = "X"

            //set animation property
            val anim = ObjectAnimator.ofFloat(btn, "rotationY", 360f).apply {
                duration = 300
                repeatCount = 2
            }
            //start animation
            AnimatorSet().apply {
                play(anim)
                start()
            }

            if (!isBoardFull(board) && !result(board, "X")) {

                val position = getComputerMove(board)
                board[position] = "O"
                displayComputerButton(position)
            }
        }
        resultOut(board)
    }

    private fun getComputerMove(board: ArrayList<String>): Int {

        //check if computer can win in this move
        for (i in 0 until board.count()) {
            val copy: ArrayList<String> = getBoardCopy(board)
            if (copy[i] == "") copy[i] = "O"
            if (result(copy, "O")) return i
        }

        //check if player could win in the next move
        for (i in 0 until board.count()) {
            val copy2 = getBoardCopy(board)
            if (copy2[i] == "") copy2[i] = "X"
            if (result(copy2, "X")) return i
        }

        //try to take corners if its free
        val move = choseRandomMove(board, arrayListOf<Int>(0, 2, 6, 8))
        if (move != -1)
            return move

        //try to take center if its free
        if (board[4] == "") return 4

        //move on one of the sides
        return choseRandomMove(board, arrayListOf<Int>(1, 3, 5, 7))
    }


    private fun choseRandomMove(board: ArrayList<String>, list: ArrayList<Int>): Int {
        val possibleMoves = arrayListOf<Int>()
        for (i in list) {
            if (board[i] == "") possibleMoves.add(i)
        }
        return if (possibleMoves.isEmpty()) -1
        else {
            val index = Random().nextInt(possibleMoves.count())
            possibleMoves[index]
        }
    }

    private fun getBoardCopy(board: ArrayList<String>): ArrayList<String> {
        val bd = arrayListOf<String>("", "", "", "", "", "", "", "", "")
        for (i in 0 until board.count()) {
            bd[i] = board[i]
        }
        return bd
    }

    private fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board)
            if (i != "X" && i != "O") return false
        return true
    }

    private fun resultOut(board: ArrayList<String>) {
        if (result(board, "X")) {
            playerWin++
            val clipDrawable = ivPlayer.drawable
            clipDrawable.level = playerWin * 10000 / 3

            if (playerWin == 3) {
                val intent = Intent(this, WinnerActivity::class.java)
                intent.putExtra("PlayerWin", playerWin)
                intent.putExtra("ComputerWin", computerWin)
                startActivity(intent)
            }
            refreshBoard()
            Toast.makeText(applicationContext, "Player win", Toast.LENGTH_SHORT).show()
        } else if (result(board, "O")) {
            computerWin++
            val clipDrawable = ivComputer.drawable
            clipDrawable.level = computerWin * 10000 / 3
            refreshBoard()
            if (computerWin == 3) {
                val intent = Intent(this, WinnerActivity::class.java)
                intent.putExtra("PlayerWin", playerWin)
                intent.putExtra("ComputerWin", computerWin)
                startActivity(intent)
            }
            refreshBoard()

            Toast.makeText(applicationContext, "Computer win", Toast.LENGTH_SHORT).show()
        }
        if (isBoardFull(board)) {
            Toast.makeText(applicationContext, "Draw", Toast.LENGTH_SHORT).show()
            refreshBoard()
        }
    }

    private fun refreshBoard() {
        buttons = Array(9) { pos ->
            initButton(pos)
        }
        board = arrayListOf("", "", "", "", "", "", "", "", "")
    }


    private fun result(bd: ArrayList<String>, s: String): Boolean =
        if (bd[0] == s && bd[1] == s && bd[2] == s) true
        else if (bd[3] == s && bd[4] == s && bd[5] == s) true
        else if (bd[6] == s && bd[7] == s && bd[8] == s) true
        else if (bd[0] == s && bd[3] == s && bd[6] == s) true
        else if (bd[1] == s && bd[4] == s && bd[7] == s) true
        else if (bd[2] == s && bd[5] == s && bd[8] == s) true
        else if (bd[0] == s && bd[4] == s && bd[8] == s) true
        else bd[2] == s && bd[4] == s && bd[6] == s


    private fun displayComputerButton(position: Int) {
        buttons[position].setImageResource(R.drawable.ic_zero_80).apply {
            buttons[position].tag = "0"
            arrayListOf("", "", "", "", "", "", "", "", "")
        }


        //set animation property
        val animX05 = ObjectAnimator.ofFloat(buttons[position], "scaleX", .1f).apply {
            duration = 300
        }
        val animY05 = ObjectAnimator.ofFloat(buttons[position], "scaleY", .1f).apply {
            duration = 300
        }
        val animX1 = ObjectAnimator.ofFloat(buttons[position], "scaleX", 1f).apply {
            duration = 300
        }
        val animY1 = ObjectAnimator.ofFloat(buttons[position], "scaleY", 1f).apply {
            duration = 300
        }
        val animSmall = AnimatorSet().apply {
            play(animX05).with(animY05)
        }
        val animBig = AnimatorSet().apply {
            play(animX1).with(animY1)
        }


        //start animation
        AnimatorSet().apply {
            playSequentially(animSmall, animBig)
            start()
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