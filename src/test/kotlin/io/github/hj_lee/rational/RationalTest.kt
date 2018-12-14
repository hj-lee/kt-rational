package io.github.hj_lee.rational

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RationalTest {
    @Test
    fun simpleForm() {
        assertEquals(1, (2 over 4).numerator.toInt())
        assertEquals(2, (2 over 4).denominator.toInt())
        assertEquals(1, (3 over 3).numerator.toInt())
        assertEquals(1, (3 over 3).denominator.toInt())
        assertEquals(1, (-2 over -4).numerator.toInt())
        assertEquals(2, (-2 over -4).denominator.toInt())
        assertEquals(-1, (2 over -4).numerator.toInt())
        assertEquals(2, (2 over -4).denominator.toInt())
        assertEquals(-1, (-2 over 4).numerator.toInt())
        assertEquals(2, (-2 over 4).denominator.toInt())
        assertEquals(1 over 4, 1 over 2 over 2)
        assertEquals(1, (3 over 2).toInt())
    }
    @Test
    fun doubleConversion() {
        assertEquals(1 over 2, 0.5.r)
        assertEquals(2 over 1, 1 over 0.5)
        assertEquals(4 over 1, 2.0 over 0.5)
        assertEquals(0.5, (1 over 2).toDouble())
        assertEquals(-0.5, (-1 over 2).toDouble())
        assertEquals(-1 over 2, (-0.5).r)

        assertEquals(1 over (2.toBigInteger().pow(100)), (1.0 / Math.pow(2.0, 100.0)).r)
        assertEquals(2.toBigInteger().pow(100).r, Math.pow(2.0, 100.0).r)

        assertEquals(((1 over 2) + (1 over 4)), (0.5 + 0.25).r)
        assertEquals((0.5 + 0.25), ((1 over 2) + (1 over 4)).toDouble())
    }

    @Test
    fun compareTo() {
        assert(1 over 2 > 1 over 3)
        assert(1 over 2 < 2 over 3)
        assert(1 over 2 == 2 over 4)
        assert(1 over 2 > -1 over 2)
    }

    @Test
    fun basicOperator() {
        assertEquals(1 over 4, (1 over 2) * (1 over 2))
        assertEquals(1 over 4, 1.r / 4)
        assertEquals(5 over 6, 1.r/2 + 1.r/3)
        assertEquals(1 over 6, 1.r/2 - 1.r/3)
        assertEquals(1 over 6, (1.r/2) * (1.r/3))
        assertEquals(3 over 2, (1.r/2) / (1.r/3))
        assertEquals(-1 over 2, -(1.r/2))
        assertEquals(1 over 4, ((-1).r/2) * (-1 over 2))
        assertEquals(2 over 1, (1 over 2).inv())
    }
}