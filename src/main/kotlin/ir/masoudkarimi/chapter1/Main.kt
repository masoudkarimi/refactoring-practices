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
        val play: Play = plays[perf.playId]!!
        var thisAmount : Int
        when (play.type) {
            "tragedy" -> {
                thisAmount = 40000
                if (perf.audience > 30) {
                    thisAmount += 1000 * (perf.audience - 30)
                }
            }

            "comedy" -> {
                thisAmount = 30000
                if (perf.audience > 20) {
                    thisAmount += 10000 + 500 * (perf.audience - 20)
                }
                thisAmount += 300 * perf.audience
            }


            else -> {
                throw Error("unknown type: ${play.type}")
            }
        }

        volumeCredits += max(perf.audience - 30, 0)
        if (play.type == "comedy") {
            volumeCredits += floor(perf.audience / 5.0).toInt()
        }

        result += " ${play.name}: ${format.format(thisAmount / 100)} (${perf.audience} seats)\n"
        totalAmount += thisAmount
    }

    result += "Amount owed is ${format.format(totalAmount / 100)}\n"
    result += "You earned $volumeCredits credits"
    return result
}