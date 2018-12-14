package io.github.hj_lee.rational

import java.io.Serializable
import java.math.BigInteger

fun BigInteger.toRational() = Rational.Companion(this, BigInteger.ONE)
fun Long.toRational() = Rational(this, 1L)
fun Int.toRational() = Rational(this, 1)
fun Double.toRational(): Rational {
    require(this.isFinite())
    require(!this.isNaN())
    // TODO proper conversion
    val bd = this.toBigDecimal()
    return Rational(bd.unscaledValue(), 10.toBigInteger().pow(bd.scale()))
}

fun Float.toRational() = this.toDouble().toRational()

fun Number.toRational(): Rational =
    when(this) {
        is Int -> this.toRational()
        is Long -> this.toRational()
        is Double -> this.toRational()
        is Float -> this.toRational()
        is BigInteger -> this.toRational()
        is Rational -> this
        else -> this.toDouble().toRational()
    }

val BigInteger.r get() = this.toRational()
val Int.r get() = this.toRational()
val Long.r get() = this.toRational()
val Double.r get() = this.toRational()
val Float.r get() = this.toRational()

val Number.r get() = this.toRational()

infix fun Int.over(d: Int) = Rational(this, d)
infix fun Long.over(d: Long) = Rational(this, d)

infix fun Number.over(d: Number) = (this.r / d.r)

data class Rational private constructor(val numerator: BigInteger, val denominator: BigInteger) : Number(),
    Comparable<Rational>, Serializable {
    companion object {
        val ZERO = Rational(BigInteger.ZERO, BigInteger.ONE)
        val ONE = Rational(BigInteger.ONE, BigInteger.ONE)
        operator fun invoke(numer: BigInteger, denom: BigInteger): Rational {
            require(denom != BigInteger.ZERO) { "Denominator cannot be null" }
            val gcd = numer.gcd(denom) * denom.signum().toBigInteger()
            return Rational(numer / gcd, denom / gcd)
        }

        operator fun invoke(n: Long, d: Long) = invoke(BigInteger.valueOf(n), BigInteger.valueOf(d))
        operator fun invoke(n: Number, d: Number) = invoke(n.toLong(), d.toLong())
    }

    override fun toString(): String =
        if (denominator == BigInteger.ONE) "$numerator" else "($numerator over $denominator)"


    override fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(other.numerator * denominator)
    }

    fun inv() = Rational.Companion(denominator, numerator)

    operator fun unaryMinus() = Rational(-numerator, denominator)
    operator fun unaryPlus() = this

    operator fun times(other: Rational) =
        Rational.Companion(numerator * other.numerator, denominator * other.denominator)

    operator fun plus(other: Rational) =
        Rational.Companion(
            numerator * other.denominator + other.numerator * denominator,
            denominator * other.denominator
        )

    operator fun minus(other: Rational) = this + (-other)

    operator fun div(other: Rational) = (this * other.inv())

    operator fun times(other: Number) = this * other.r
    operator fun plus(other: Number) = this + other.r
    operator fun minus(other: Number) = this - other.r
    operator fun div(other: Number) = this / other.r

    fun toRational() = this
    val r get() = this

    infix fun over(other: Number) = this / other.r

    fun toBigInteger() = numerator / denominator

    override fun toByte() = toBigInteger().toByte()

    override fun toChar() = toBigInteger().toChar()

    override fun toInt() = toBigInteger().toInt()

    override fun toLong() = toBigInteger().toLong()

    override fun toShort() = toBigInteger().toShort()

    override fun toDouble() = numerator.toDouble() / denominator.toDouble()

    override fun toFloat() = toDouble().toFloat()
}