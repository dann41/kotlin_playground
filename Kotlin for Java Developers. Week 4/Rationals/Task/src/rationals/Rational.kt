package rationals

import java.math.BigInteger

class Rational(private var numerator: BigInteger, private var denominator: BigInteger) : Comparable<Rational> {

    init {
        normalize()
    }

    operator fun plus(other: Rational): Rational {
        return if (other.denominator == this.denominator) {
            Rational(
                    this.numerator + other.numerator,
                    this.denominator
            )
        } else {
            Rational(
                    this.numerator * other.denominator + other.numerator * this.denominator,
                    this.denominator * other.denominator
            )
        }
    }

    operator fun minus(other: Rational): Rational {
        return if (other.denominator == this.denominator) {
            Rational(
                    this.numerator - other.numerator,
                    this.denominator
            )
        } else {
            Rational(
                    this.numerator * other.denominator - other.numerator * this.denominator,
                    this.denominator * other.denominator
            )
        }
    }

    operator fun times(other: Rational): Rational {
        return Rational(numerator * other.numerator, denominator * other.denominator)
    }

    operator fun div(other: Rational): Rational {
        return Rational(numerator * other.denominator, denominator * other.numerator)
    }

    operator fun unaryMinus(): Rational = Rational(-numerator, denominator)

    override fun compareTo(other: Rational): Int {
        val num1 = numerator * other.denominator
        val num2 = other.numerator * denominator
        return num1.compareTo(num2)
    }

    operator fun rangeTo(other: Rational): ClosedRange<Rational> {
        var start = this
        return object: ClosedRange<Rational> {
            override val endInclusive: Rational
                get() = other
            override val start: Rational
                get() = start

        }
    }

    private fun normalize() : Rational {
        val numeratorNegative = numerator.abs() != numerator
        val denominatorNegative = denominator.abs() != denominator
        val sign = if (numeratorNegative != denominatorNegative) (-1).toBigInteger() else 1.toBigInteger()
        val gcd = numerator.abs().gcd(denominator.abs())

        numerator = sign * (numerator.abs() / gcd)
        denominator = denominator.abs() / gcd
        return this
    }

    override fun toString(): String {
        if (denominator == BigInteger.ONE)
            return "$numerator"
        return "$numerator/$denominator"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        return other.numerator == this.numerator && other.denominator == this.denominator
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

}


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

fun String.toRational(): Rational {
    var parts = this.split("/")
    if (parts.size == 2)
        return Rational(BigInteger(parts[0]), BigInteger(parts[1]))
    return Rational(BigInteger(parts[0]), BigInteger.ONE)
}

infix fun BigInteger.divBy(i: BigInteger) = Rational(this, i)
infix fun Long.divBy(i: Long) = Rational(this.toBigInteger(), i.toBigInteger())
infix fun Int.divBy(i: Int) = Rational(this.toBigInteger(), i.toBigInteger())
