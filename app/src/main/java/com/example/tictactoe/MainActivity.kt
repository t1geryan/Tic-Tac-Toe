package com.example.tictactoe

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.tictactoe.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private var currentGlobalPlayer = 1 // Red or Blue
    private var currentPlayer = 1 // X or O
    private lateinit var board: Board

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        board = Board(listOf(
            listOf(binding.cellNW, binding.cellN, binding.cellNE),
            listOf(binding.cellW, binding.cellC, binding.cellE),
            listOf(binding.cellSW, binding.cellS, binding.cellSE)))

        board.forEachCell { cell ->
            cell.setOnClickListener { makeMove(cell) }
        }

        binding.resetBtn.setOnClickListener { showResetAlertDialog() }
    }

    // --- game manager functions

    private fun resetGame() {
        setHelpBars(currentGlobalPlayer)
        newGame()
        binding.firstPlayerScore.text = "0"
        binding.secondPlayerScore.text = "0"
    }

    private fun makeMove(cell: TextView) {
        if (cell.text.isEmpty()) {
            val lastPlayer = board.getPlayer()
            board.set(cell, currentPlayer)
            currentPlayer *= -1
            setHelpBars(lastPlayer)
            if (board.isWin()) {
                showToast("Winner is ${if (lastPlayer == 1) "Blue" else "Red"}")
                when (lastPlayer) {
                    1 -> upScore(binding.firstPlayerScore)
                    -1 -> upScore(binding.secondPlayerScore)
                }
                newGame()
            } else if (board.isFull()) {
                showToast("It's a Draw")
                newGame()
            }
        }
    }

    private fun newGame() {
        Thread.sleep(100)
        board.clear()
        setHelpBars(currentGlobalPlayer)
        currentGlobalPlayer *= -1
        currentPlayer = 1
        board.setFirstPlayer(currentGlobalPlayer)

    }

    // --- dialog functions

    private fun showResetAlertDialog() {
        val listener = DialogInterface.OnClickListener { _, whichButton ->
            when (whichButton) {
                DialogInterface.BUTTON_POSITIVE -> resetGame()
                DialogInterface.BUTTON_NEGATIVE -> showToast("Game Continues")
            }
        }
        AlertDialog.Builder(this)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle("Reset Game")
            .setMessage("Do you sure to reset game?")
            .setPositiveButton("Yes", listener)
            .setNegativeButton("No", listener)
            .create()
            .show()
    }

    // --- function helpers


    @SuppressLint("SetTextI18n")
    private fun upScore(scoreView: TextView) {
        val score = scoreView.text.toString().toInt()
        scoreView.text = "${score+1}"
    }

    private fun setHelpBars(lastPlayer: Int) {
        when (lastPlayer) {
            1 -> {
                binding.redPlayerBar.visibility = View.VISIBLE
                binding.bluePlayerBar.visibility = View.INVISIBLE
            }
            -1 -> {
                binding.redPlayerBar.visibility = View.INVISIBLE
                binding.bluePlayerBar.visibility = View.VISIBLE
            }
        }
    }

    private fun showToast(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}