package week2

fun main(args: Array<String>) {
    println("Hello, ${args.getOrElse(0) { "Kotlin" }}!")
}