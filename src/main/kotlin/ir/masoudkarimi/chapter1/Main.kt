package ir.masoudkarimi.ir.masoudkarimi.chapter1

import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.max

fun main() {
    for (invoice in invoices) {
        println(statement(invoice, plays))
    }
}


fun statement(invoice: Invoice, plays: Plays): String {
    var totalAmount = 0
    var volumeCredits = 0
    var result = "Statement for ${invoice.customer}\n"
    val format = NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = 2
    }
    for (perf in invoice.performances) {
        val thisAmount = amountFor(playFor(perf), perf)

        volumeCredits += max(perf.audience - 30, 0)
        if (playFor(perf).type == "comedy") {
            volumeCredits += floor(perf.audience / 5.0).toInt()
        }

        result += " ${playFor(perf).name}: ${format.format(thisAmount / 100)} (${perf.audience} seats)\n"
        totalAmount += thisAmount
    }

    result += "Amount owed is ${format.format(totalAmount / 100)}\n"
    result += "You earned $volumeCredits credits"
    return result
}


private fun amountFor(play: Play, performance: Performance): Int {
    var result: Int

    when (playFor(performance).type) {
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
            throw Error("unknown type: ${playFor(performance).type}")
        }
    }

    return result
}

private fun playFor(performance: Performance) = plays[performance.playId]!!
