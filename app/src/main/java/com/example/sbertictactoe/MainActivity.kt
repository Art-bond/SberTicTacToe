package com.example.sbertictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var buttons: Array<Array<Button>>
    lateinit var tvPlayerOne: TextView
    lateinit var tvPlayerTwo: TextView

    private var player1Turn: Boolean = true
    private var roundCount: Int = 0
    private var player1Points: Int = 0
    private var player2Points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPlayerOne = findViewById(R.id.tv_pl_one)
        tvPlayerTwo = findViewById(R.id.tv_pl_two)

        //init playing buttons
        buttons = Array(3) { row ->
            Array(3) { column ->
                initButton(row, column)
            }
        }

        val btnReset: Button = findViewById(R.id.btn_reset)

        btnReset.setOnClickListener {
            Toast.makeText(applicationContext, "Reset button clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initButton(row: Int, column: Int): Button {
        val btn: Button =
                findViewById(resources.getIdentifier("btn$row$column", "id", packageName))
        btn.setOnClickListener {
            Toast.makeText(
                    applicationContext,
                    "button $row$column clicked",
                    Toast.LENGTH_SHORT).show()
        }
        return btn
    }


}