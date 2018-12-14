package io.github.hj_lee.rational

import java.io.Serializable
import java.math.BigInteger

infix fun Number.over(d: Number) = Rational(this, d)

fun Number.toRational() = Rational(this, 1)
fun Double.toRational(): Rational {
    require(this.isFinite())
    require(!this.isNaN())
    // TODO proper conversion
    val bd = this.toBigDecimal()
    return Rational(bd.unscaledValue(), 10.toBigInteger().pow(bd.scale()))
}

fun Float.toRational() = this.toDouble().toRational()

val Number.r get() = this.toRational()
val Double.r get() = this.toRational()
val Float.r get() = this.toRational()

data class Rational private constructor(val numerator: BigInteger, val denominator: BigInteger) : Comparable<Rational>, Serializable {
    companion object {
        val ZERO = Rational(BigInteger.ZERO, BigInteger.ONE)
        val ONE = Rational(BigInteger.ONE, BigInteger.ONE)
        operator fun invoke(numer: BigInteger, denom: BigInteger): Rational {
            require(denom != BigInteger.ZERO)
            val gcd = numer.gcd(denom) * denom.signum().toBigInteger()
            return Rational(numer / gcd, denom / gcd)
        }
        operator fun invoke(n: Long, d: Long) = invoke(BigInteger.valueOf(n), BigInteger.valueOf(d))
        operator fun invoke(n: Number, d: Number) = invoke(n.toLong(), d.toLong())
    }

    override fun compareTo(other: Rational): Int {
        return (numerator * other.denominator).compareTo(other.numerator * denominator)
    }

    fun toBigInteger() = numerator / denominator

    fun toByte() = toBigInteger().toByte()

    fun toChar() = toBigInteger().toChar()

    fun toInt() = toBigInteger().toInt()

    fun toLong() = toBigInteger().toLong()

    fun toShort() = toBigInteger().toShort()

    fun toDouble() = numerator.toDouble() / denominator.toDouble()

    fun toFloat() = toDouble().toFloat()
}