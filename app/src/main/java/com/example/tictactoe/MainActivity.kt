package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                if (board.isNotEmpty())
                    newGame(board)
            }
        }

    }

    private fun makeMove(cell: TextView, board: Board) {
        board.set(cell, currentPlayer)
        val lastPlayer = currentPlayer
        changeCurrentPlayer()
        if (board.isWin()) {
            Toast.makeText(applicationContext, "Winner is ${if (-board.getPlayer() == 1) "Blue" else "Red"}", Toast.LENGTH_SHORT).show()
            newGame(board)
        } else if (board.isFull()){
            Toast.makeText(applicationContext, "It's a Draw", Toast.LENGTH_SHORT).show()
            newGame(board)
        }
    }

    private fun newGame(board: Board) {
        Thread.sleep(500)
        board.clear()
        currentGlobalPlayer *= -1
        currentPlayer = 1
        board.setFirstPlayer(currentGlobalPlayer)
    }

    private fun changeCurrentPlayer() {
        currentPlayer *= -1
    }
}