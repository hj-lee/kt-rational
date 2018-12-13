package io.github.hj_lee.rational

import java.io.Serializable
import java.math.BigInteger

infix fun Number.over(d: Number) = Rational(this, d)

fun Number.toRational() = Rational(this, 1)

class Rational(numer: BigInteger, denom: BigInteger) : Comparable<Rational>, Serializable {
    val numerator: BigInteger
    val denominator: BigInteger

    companion object {
        val ZERO = Rational(BigInteger.ZERO, BigInteger.ONE)
        val ONE = Rational(BigInteger.ONE, BigInteger.ONE)
    }

    init {
        require(denom != BigInteger.ZERO)
        val gcd = numer.gcd(denom) * denom.signum().toBigInteger()
        numerator = numer / gcd
        denominator = denom / gcd
    }

    constructor(n: Long, d: Long) : this(BigInteger.valueOf(n), BigInteger.valueOf(d))
    constructor(n: Number, d: Number) : this(n.toLong(), d.toLong())

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