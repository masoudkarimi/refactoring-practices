package ir.masoudkarimi.ir.masoudkarimi.chapter1

import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.properties.Delegates

fun main() {
    for (invoice in invoices) {
        println(statement(invoice, plays))
    }
}

data class StatementData(
    val customer: String,
    val performances: List<EnrichPerformance>
)

data class EnrichPerformance(
    val playId: String,
    val audience: Int,
) {
    var play: Play by Delegates.notNull()
    var amount: Int by Delegates.notNull()
}

fun statement(invoice: Invoice, plays: Plays): String {
    fun enrichPerformance(performance: Performance): EnrichPerformance {
        fun playFor(performance: EnrichPerformance) = plays[performance.playId]!!

        fun amountFor(performance: EnrichPerformance): Int {
            var result: Int

            when (performance.play.type) {
                "tragedy" -> {
                    result = 40000
                    if (performance.audience > 30) {
                        result += 1000 * (performance.audience - 30)
                    }
                }

                "comedy" -> {
                    result = 30000
                    if (performance.audience > 20) {
                        result += 10000 + 500 * (performance.audience - 20)
                    }
                    result += 300 * performance.audience
                }


                else -> {
                    throw Error("unknown type: ${performance.play.type}")
                }
            }

            return result
        }

        val enrichPerformance = EnrichPerformance(
            playId = performance.playId,
            audience = performance.audience,
        )

        enrichPerformance.play = playFor(enrichPerformance)
        enrichPerformance.amount = amountFor(enrichPerformance)

        return enrichPerformance
    }

    val statementData = StatementData(
        customer = invoice.customer,
        performances = invoice.performances.map(::enrichPerformance)
    )

    return renderPlainText(statementData, plays)
}


fun renderPlainText(data: StatementData, plays: Plays): String {

    fun totalAmount(): Int {
        var result = 0
        for (perf in data.performances) {
            result += perf.amount
        }
        return result
    }

    fun usd(number: Int): String {
        return NumberFormat.getCurrencyInstance(Locale.US).apply {
            minimumFractionDigits = 2
        }.format(number / 100)
    }

    fun volumeCreditsFor(performance: EnrichPerformance): Int {
        var result = 0
        result += max(performance.audience - 30, 0)
        if (performance.play.type == "comedy") {
            result += floor(performance.audience / 5.0).toInt()
        }

        return result
    }

    fun totalVolumeCredits(): Int {
        var result = 0
        for (perf in data.performances) {
            result += volumeCreditsFor(perf)
        }
        return result
    }

    var result = "Statement for ${data.customer}\n"
    for (perf in data.performances) {
        result += " ${perf.play.name}: ${usd(perf.amount)} (${perf.audience} seats)\n"
    }

    result += "Amount owed is ${usd(totalAmount())}\n"
    result += "You earned ${totalVolumeCredits()} credits"
    return result
}
