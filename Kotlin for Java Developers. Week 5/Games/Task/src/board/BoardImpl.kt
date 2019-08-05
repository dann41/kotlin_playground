package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    private val cells: Array<Array<Cell>> = Array(width) { i -> Array(width) { j -> Cell(i + 1, j + 1) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.getOrNull(i - 1)?.getOrNull(j - 1)

    override fun getCell(i: Int, j: Int): Cell {
        if (i > width || j > width)
            throw IllegalArgumentException()
        return cells[i - 1][j - 1]
    }

    override fun getAllCells(): Collection<Cell> = cells.flatten()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val result = cells[i - 1].filter { cell -> cell.j in jRange }

        if (jRange.first > jRange.last)
            return result.reversed()
        return result
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val result = cells.filterIndexed { index, _ -> iRange.contains(index + 1) }
                .map { row -> row[j - 1] }

        if (iRange.first > iRange.last)
            return result.reversed()
        return result
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(this.i - 1, this.j)
            DOWN -> getCellOrNull(this.i + 1, this.j)
            RIGHT -> getCellOrNull(this.i, this.j + 1)
            LEFT -> getCellOrNull(this.i, this.j - 1)
        }
    }

}

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {

    private val cellsMap: HashMap<Cell, T?> = HashMap();

    override fun get(cell: Cell): T? = cellsMap[cell]

    override fun set(cell: Cell, value: T?) {
        cellsMap[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = getAllCells()
            .map { cell -> (cell to cellsMap[cell]) }
            .filter { (_, t) -> predicate.invoke(t) }
            .map { (cell, _) -> cell}

    override fun find(predicate: (T?) -> Boolean): Cell? = getAllCells()
            .map { cell -> (cell to cellsMap[cell]) }
            .find { (_, t) -> predicate.invoke(t) }
            ?.component1()

    override fun any(predicate: (T?) -> Boolean): Boolean = getAllCells()
            .map { cell -> (cell to cellsMap[cell]) }
            .any { (_, t) -> predicate.invoke(t) }

    override fun all(predicate: (T?) -> Boolean): Boolean = getAllCells()
            .map { cell -> (cell to cellsMap[cell]) }
            .all { (_, t) -> predicate.invoke(t) }

}

