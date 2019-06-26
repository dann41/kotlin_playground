package week2

fun main() {
    println(listOf('a', 'b', 'c').joinToString(
        separator = "",
        prefix = "(",
        postfix = ")"
    ))

    displaySeparator('a', 5)
    println()
    displaySeparator('b')
    println()
    displaySeparator(size = 4)
}

fun displaySeparator(character: Char = '*', size: Int = 10) {
    repeat(size) {
        print(character)
    }
}