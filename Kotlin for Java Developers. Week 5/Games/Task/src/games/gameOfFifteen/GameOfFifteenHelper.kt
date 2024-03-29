package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    fun permutationsPerValue(index: Int, value: Int) : Int {
        return permutation.filterIndexed { j, _ -> index < j }
                .count { v -> v < value }
    }

    return permutation
            .mapIndexed { i, v -> permutationsPerValue(i, v) }
            .sum() % 2 == 0
}