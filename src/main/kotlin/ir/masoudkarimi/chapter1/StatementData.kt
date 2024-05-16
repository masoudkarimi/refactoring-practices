package ir.masoudkarimi.ir.masoudkarimi.chapter1

import kotlin.math.floor
import kotlin.math.max
import kotlin.properties.Delegates

data class StatementData(
    val customer: String,
    val performances: List<EnrichPerformance>,
) {
    var totalAmount: Int by Delegates.notNull()
    var totalVolumeCredits: Int by Delegates.notNull()
}

data class EnrichPerformance(
    val playId: String,
    val audience: Int,
) {
    var play: Play by Delegates.notNull()
    var amount: Int by Delegates.notNull()
    var volumeCredits: Int by Delegates.notNull()
}

open class PerformanceCalculator(
    val performance: Performance,
    val play: Play
) {

    open fun amount(): Int {
        throw IllegalStateException("Subclass responsibility!")
    }

    fun volumeCredits(): Int {
        var result = 0
        result += max(performance.audience - 30, 0)
        if (play.type == "comedy") {
            result += floor(performance.audience / 5.0).toInt()
        }

        return result
    }

}

class TragedyPerformanceCalculator(
    performance: Performance,
    play: Play
) : PerformanceCalculator(performance, play) {

    override fun amount(): Int {
        var result = 40000
        if (performance.audience > 30) {
            result += 1000 * (performance.audience - 30)
        }

        return result
    }
}

class ComedyPerformanceCalculator(
    performance: Performance,
    play: Play
) : PerformanceCalculator(performance, play) {

    override fun amount(): Int {
        var result = 30000
        if (performance.audience > 20) {
            result += 10000 + 500 * (performance.audience - 20)
        }
        result += 300 * performance.audience
        return result
    }
}

fun createStatementData(invoice: Invoice, plays: Plays): StatementData {

    fun createPerformanceCalculator(performance: Performance, play: Play): PerformanceCalculator {
        return when (play.type) {
            "tragedy" -> TragedyPerformanceCalculator(performance, play)
            "comedy" -> ComedyPerformanceCalculator(performance, play)
            else -> throw IllegalArgumentException("Unknown type: ${play.type}")
        }
    }

    fun enrichPerformance(performance: Performance): EnrichPerformance {
        fun playFor(performance: Performance) = plays[performance.playId]!!
        val calculator = createPerformanceCalculator(performance, playFor(performance))
        val enrichPerformance = EnrichPerformance(
            playId = performance.playId,
            audience = performance.audience,
        )


        enrichPerformance.play = calculator.play
        enrichPerformance.amount = calculator.amount()
        enrichPerformance.volumeCredits = calculator.volumeCredits()
        return enrichPerformance
    }

    fun totalAmount(data: StatementData): Int {
        return data.performances.sumOf(EnrichPerformance::amount)
    }

    fun totalVolumeCredits(data: StatementData): Int {
        return data.performances.sumOf(EnrichPerformance::volumeCredits)
    }

    val statementData = StatementData(
        customer = invoice.customer,
        performances = invoice.performances.map(::enrichPerformance),
    )

    statementData.totalAmount = totalAmount(statementData)
    statementData.totalVolumeCredits = totalVolumeCredits(statementData)

    return statementData
}