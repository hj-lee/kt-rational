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
    }

    @Test
    fun compareTo() {
        val r1o2 = Rational(1, 2)
        val r1o3 = Rational(1, 3)
        val r2o3 = Rational(2, 3)
        assert(r1o2 > r1o3)
        assert(r1o2 < r2o3)
    }
}