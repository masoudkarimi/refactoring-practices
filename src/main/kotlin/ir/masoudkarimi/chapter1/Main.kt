package ir.masoudkarimi.ir.masoudkarimi.chapter1

import java.text.NumberFormat
import java.util.*

fun main() {
    for (invoice in invoices) {
        println(statement(invoice, plays))
    }
}

fun usd(number: Int): String {
    return NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = 2
    }.format(number / 100)
}

fun statement(invoice: Invoice, plays: Plays): String {
    return renderPlainText(createStatementData(invoice, plays))
}

fun renderPlainText(data: StatementData): String {

    var result = "Statement for ${data.customer}\n"
    for (perf in data.performances) {
        result += " ${perf.play.name}: ${usd(perf.amount)} (${perf.audience} seats)\n"
    }

    result += "Amount owed is ${usd(data.totalAmount)}\n"
    result += "You earned ${data.totalVolumeCredits} credits"
    return result
}
