package ir.masoudkarimi.chapter1

import ir.masoudkarimi.ir.masoudkarimi.chapter1.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Chapter1StatementTest {

    val invoices = listOf(
        Invoice(
            customer = "BigCo",
            performances = listOf(
                Performance(
                    playId = "hamlet",
                    audience = 55
                ),
                Performance(
                    playId = "as-like",
                    audience = 35
                ),
                Performance(
                    playId = "othello",
                    audience = 40
                )
            )
        ),
        Invoice(
            customer = "Com1",
            performances = listOf(
                Performance(
                    playId = "hamlet",
                    audience = 10
                ),
                Performance(
                    playId = "as-like",
                    audience = 100
                ),
                Performance(
                    playId = "othello",
                    audience = 15
                )
            )
        ),
        Invoice(
            customer = "Com2",
            performances = listOf(
                Performance(
                    playId = "as-like",
                    audience = 25
                ),
                Performance(
                    playId = "othello",
                    audience = 10
                )
            )
        ),
        Invoice(
            customer = "Com2",
            performances = listOf(
                Performance(
                    playId = "hamlet",
                    audience = 4
                ),
                Performance(
                    playId = "as-like",
                    audience = 200
                ),
                Performance(
                    playId = "othello",
                    audience = 121
                )
            )
        )
    )

    @Test
    fun expectedValue1() {
        val expected = """Statement for BigCo
 Hamlet: ${'$'}650.00 (55 seats)
 As You Like It: ${'$'}580.00 (35 seats)
 Othello: ${'$'}500.00 (40 seats)
Amount owed is ${'$'}1,730.00
You earned 47 credits""".trimIndent()
        val actual = statement(invoices[0], plays)
        assertEquals(expected, actual)
    }

    @Test
    fun expectedValue2() {
        val expected = """Statement for Com1
 Hamlet: ${'$'}400.00 (10 seats)
 As You Like It: ${'$'}1,100.00 (100 seats)
 Othello: ${'$'}400.00 (15 seats)
Amount owed is ${'$'}1,900.00
You earned 90 credits""".trimIndent()
        val actual = statement(invoices[1], plays)
        assertEquals(expected, actual)
    }

    @Test
    fun expectedValue3() {
        val expected = """Statement for Com2
 As You Like It: ${'$'}500.00 (25 seats)
 Othello: ${'$'}400.00 (10 seats)
Amount owed is ${'$'}900.00
You earned 5 credits""".trimIndent()
        val actual = statement(invoices[2], plays)
        assertEquals(expected, actual)
    }

    @Test
    fun expectedValue4() {
        val expected = """Statement for Com2
 Hamlet: ${'$'}400.00 (4 seats)
 As You Like It: ${'$'}1,900.00 (200 seats)
 Othello: ${'$'}1,310.00 (121 seats)
Amount owed is ${'$'}3,610.00
You earned 301 credits""".trimIndent()
        val actual = statement(invoices[3], plays)
        assertEquals(expected, actual)
    }
}