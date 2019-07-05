package nicestring

fun String.isNice(): Boolean = listOf(
            listOf("ba", "be", "bu").none { it in this },
            this.count { it in "aeiou" } >= 3,
            this.zipWithNext().any { (current, next) -> current == next }
    ).count { it } >= 2