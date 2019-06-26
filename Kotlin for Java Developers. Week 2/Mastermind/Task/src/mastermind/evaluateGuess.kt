package mastermind

import kotlin.math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    // easiest scenario: same word
    if (secret == guess) {
        return Evaluation(secret.length, 0)
    }

    var rightPosition = 0
    val nonExactMatchingInSecret = mapOf<Char, Int>().toMutableMap()
    val nonExactMatchingInGuess = mapOf<Char, Int>().toMutableMap()

    // count right positions and build mismatches map
    for ((a, b) in secret.zip(guess)) {
        if (a == b) {
            rightPosition++
        } else {
            nonExactMatchingInSecret.compute(a) { _, count -> count?.plus(1) ?: 1}
            nonExactMatchingInGuess.compute(b) { _, count -> count?.plus(1) ?: 1}
        }
    }

    // mix the mismatches and count coincidences
    val wrongPosition = nonExactMatchingInSecret.keys.intersect(nonExactMatchingInGuess.keys)
            .map { min(nonExactMatchingInGuess[it] ?: 0, nonExactMatchingInSecret[it] ?: 0) }
            .sum()

    return Evaluation(rightPosition, wrongPosition)
}
