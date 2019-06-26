package week2

fun main() {
    testIf(3, 4)
    testIf(4, 3)

    testWhen(5)
    testWhen(18)
    testWhen(20)
    testWhen(67)
}

fun testIf(a: Int, b: Int)
{
    // no ternary
    if (a < b)
        println("A is smaller")
    else
        println("B is smaller")
}

fun testWhen(age: Int)
{
    when (age) {
        in IntRange(1, 17) -> println("Happy birthday! Keep playing")
        18 -> println("Happy 18! You're now an adult")
        67 -> println("Happy retirement")
        else -> println("Happy birthday")
    }
}