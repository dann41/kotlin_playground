package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        board.getAllCells()
                .zip(initializer.initialPermutation)
                .forEach { (cell, value) -> board[cell] = value}
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        val solution = Array<Int?>(16) {
            i -> if (i != 15) i + 1 else null
        }

        return solution
                .zip(board.getAllCells().map { cell -> board[cell] })
                .all { (expected, actual) -> expected == actual }
    }

    override fun processMove(direction: Direction) {
        val emptyCell = board.find { it == null }
        val neighbourCell = emptyCell?.let { getNeighbourCell(it, direction) }

        if (neighbourCell != null) {
            board[emptyCell] = board[neighbourCell]
            board[neighbourCell] = null
        }
    }

    private fun getNeighbourCell(cell: Cell, direction: Direction): Cell? {
        return when(direction) {
            Direction.UP -> board.getCellOrNull(cell.i + 1, cell.j)
            Direction.DOWN -> board.getCellOrNull(cell.i - 1, cell.j)
            Direction.RIGHT -> board.getCellOrNull(cell.i, cell.j - 1)
            Direction.LEFT -> board.getCellOrNull(cell.i, cell.j + 1)
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return board[board.getCell(i, j)]
    }

}

