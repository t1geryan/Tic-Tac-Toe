package com.example.tictactoe

import android.graphics.Color
import android.widget.TextView
import java.lang.IllegalArgumentException


class Board(
    private val board: List<List<TextView>>,
    private var player: Int = 1
) {
    fun getPlayer(): Int {
        return player
    }
    fun clear() {
        board.forEach {
            it.forEach { view ->
                view.text = ""
            }
        }
    }

    fun isEmpty(): Boolean {
        var result = true
        for (row in board)
            for (cell in row)
                result = result and cell.text.isEmpty()
        return result
    }

    fun isNotEmpty(): Boolean {
        return !isEmpty()
    }

    fun isFull(): Boolean {
        var result = true
        for (row in board)
            for (cell in row)
                result = result and cell.text.isNotEmpty()
        return result
    }

    fun set(cell: TextView, value: Int) {
        if (cell.text.isEmpty()) {
            setValue(cell, value, player)
            changePlayer()
        }
    }

    fun isWin(): Boolean {
        val isWinLine = { cell1: TextView, cell2:TextView, cell3:TextView -> Boolean
            cell1.text.isNotEmpty() and (cell1.text == cell2.text) and (cell2.text == cell3.text)
        }


        var result = false
        if (isNotEmpty()) {
            for (i in board.indices)
                result = result or isWinLine(board[i][0], board[i][1], board[i][2]) or isWinLine(board[0][i], board[1][i], board[2][i])
            result = result or isWinLine(board[0][0], board[1][1], board[2][2]) or isWinLine(board[0][2], board[1][1], board[2][0])
        }
        return result
    }

    fun setFirstPlayer(value: Int) {
        player = value
    }
    fun forEachCell( lambda: (TextView) -> Unit) {
        board.forEach {
            it.forEach { view ->
                lambda(view)
            }
        }
    }

    private fun setValue(cell: TextView, value: Int, player: Int) {
        setPlayerColor(cell, player)
        when (value) {
            -1 -> cell.text = "O"
            0 -> cell.text = ""
            1 -> cell.text = "X"
            else -> throw IllegalArgumentException()
        }
    }

    private fun setPlayerColor(cell: TextView, player: Int) {
        when (player) {
            -1 -> cell.setTextColor(Color.RED)
            1 -> cell.setTextColor(Color.BLUE)
            else -> throw  IllegalArgumentException()
        }
    }
    private fun changePlayer() {
        player *= -1
    }

}