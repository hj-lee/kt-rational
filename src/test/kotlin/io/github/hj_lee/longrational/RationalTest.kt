package io.github.hj_lee.longrational

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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


        assertEquals(((1 over 2) + (1 over 4)), (0.5 + 0.25).r)
        assertEquals((0.5 + 0.25), ((1 over 2) + (1 over 4)).toDouble())
    }

    @Test
    fun doubleOutOfRange() {
        assertEquals(1L over 1L.shl(62), (1.0 / Math.pow(2.0, 62.0)).r)
        assertEquals(0 over 1, (1.0 / Math.pow(2.0, 63.0)).r)
        assertEquals(1L.shl(62) over 1L, Math.pow(2.0, 62.0).r)
        assertThrows<IllegalArgumentException> {
            Math.pow(2.0, 63.0).r
        }
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
        assertEquals(5 over 6, 1.r / 2 + 1.r / 3)
        assertEquals(1 over 6, 1.r / 2 - 1.r / 3)
        assertEquals(1 over 6, (1.r / 2) * (1.r / 3))
        assertEquals(3 over 2, (1.r / 2) / (1.r / 3))
        assertEquals(-1 over 2, -(1.r / 2))
        assertEquals(1 over 4, ((-1).r / 2) * (-1 over 2))
        assertEquals(2 over 1, (1.r / 2).inv())

        assertEquals(2 over 3, 2 / 3.r)
        assertEquals(1 over 1, 2 * (1 over 2))
        assertEquals(3 over 2, 1 + 1 / 2.r)
        assertEquals(1 over 2, 1 - 1 / 2.r)
    }
}