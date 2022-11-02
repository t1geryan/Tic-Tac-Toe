package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.CellIdentity
import android.view.View
import android.widget.TextView
import com.example.tictactoe.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var currentGlobalPlayer = 1 // Red or Blue
    private var currentPlayer = 1 // X or O

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val board = Board(listOf(
            listOf(binding.cellNW, binding.cellN, binding.cellNE),
            listOf(binding.cellW, binding.cellC, binding.cellE),
            listOf(binding.cellSW, binding.cellS, binding.cellSE)))

        board.forEachCell { cell ->
            cell.setOnClickListener {
                makeMove(cell, board)
            }
        }

        with(binding.resetBtn) {
            setOnClickListener {
                when(currentGlobalPlayer) {
                    1 -> {
                        binding.redPlayerBar.visibility = View.VISIBLE
                        binding.bluePlayerBar.visibility = View.INVISIBLE
                    }
                    -1 -> {
                        binding.redPlayerBar.visibility = View.INVISIBLE
                        binding.bluePlayerBar.visibility = View.VISIBLE
                    }
                }
                newGame(board)
                binding.firstPlayerScore.text = "0"
                binding.secondPlayerScore.text = "0"

            }
        }

    }


    private fun makeMove(cell: TextView, board: Board) {
        if (cell.text.isEmpty()) {
            board.set(cell, currentPlayer)
            val lastPlayer = -board.getPlayer()
            changeCurrentPlayer()
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
            if (board.isWin()) {
                Toast.makeText(
                    applicationContext,
                    "Winner is ${if (lastPlayer == 1) "Blue" else "Red"}",
                    Toast.LENGTH_SHORT
                ).show()
                when (lastPlayer) {
                    1 -> upScore(binding.firstPlayerScore)
                    -1 -> upScore(binding.secondPlayerScore)
                }

                newGame(board)
            } else if (board.isFull()) {
                Toast.makeText(applicationContext, "It's a Draw", Toast.LENGTH_SHORT).show()
                newGame(board)
            }
        }
    }

    private fun upScore(scoreView: TextView) {
        val score = scoreView.text.toString().toInt()
        scoreView.text = (score+1).toString()
    }
    private fun newGame(board: Board) {
        Thread.sleep(100)
        board.clear()
        currentGlobalPlayer *= -1
        currentPlayer = 1
        board.setFirstPlayer(currentGlobalPlayer)
    }

    private fun changeCurrentPlayer() {
        currentPlayer *= -1
    }
}