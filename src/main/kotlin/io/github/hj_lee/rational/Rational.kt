package io.github.hj_lee.rational

import java.io.Serializable
import java.math.BigInteger

fun BigInteger.toRational() = Rational.Companion(this, BigInteger.ONE)
fun Long.toRational() = Rational(this, 1L)
fun Int.toRational() = Rational(this, 1)


private const val BUT_SIGN_BIT = (-1L).ushr(1)
private const val FRACTION_MASK = (-1L).ushr(12)
private const val FRACTION_1 = 1L.shl(52)
fun Double.toRational(): Rational {
    require(this.isFinite())
    require(!this.isNaN())
    if (this == 0.0) return Rational.ZERO
    val bits = this.toBits()
    val exponent = bits.and(BUT_SIGN_BIT).ushr(52) - 1023 - 52
    val fraction = bits.and(FRACTION_MASK).or(FRACTION_1)
    val abs = if (exponent >=0 ) Rational(fraction.toBigInteger() * 2.toBigInteger().pow(exponent.toInt()), BigInteger.ONE)
    else Rational(fraction.toBigInteger(), 2.toBigInteger().pow(-exponent.toInt()))
    return if (bits < 0) -abs else abs
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

operator fun Number.times(r: Rational) = this.r * r
operator fun Number.div(r: Rational) = this.r / r
operator fun Number.plus(r: Rational) = this.r + r
operator fun Number.minus(r: Rational) = this.r - r

class Rational private constructor(val numerator: BigInteger, val denominator: BigInteger) : Number(),
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

    // Number functions

    override fun toByte() = toBigInteger().toByte()

    override fun toChar() = toBigInteger().toChar()

    override fun toInt() = toBigInteger().toInt()

    override fun toLong() = toBigInteger().toLong()

    override fun toShort() = toBigInteger().toShort()

    override fun toDouble() = numerator.toDouble() / denominator.toDouble()

    override fun toFloat() = toDouble().toFloat()

    ////////

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }
}