package ir.masoudkarimi.ir.masoudkarimi.chapter1

import java.text.NumberFormat
import java.util.*


fun usd(number: Int): String {
    return NumberFormat.getCurrencyInstance(Locale.US).apply {
        minimumFractionDigits = 2
    }.format(number / 100)
}

fun statement(invoice: Invoice, plays: Plays): String {
    return renderPlainText(createStatementData(invoice, plays))
}

fun htmlStatement(invoice: Invoice, plays: Plays): String {
    return renderHtml(createStatementData(invoice, plays))
}

fun renderHtml(data: StatementData): String {

    var result = "<h1>Statement for ${data.customer}</h1>\n"
    result += "<table>\n"
    result += "<tr><th>play</th><th>seats</th><th>cost</th></tr>"
    for (perf in data.performances) {
        result += " <tr><td>${perf.play.name}</td><td>${perf.audience}</td>"
        result += "<td>${usd(perf.amount)}</td></tr>\n"
    }
    result += "</table>\n"
    result += "<p>Amount owed is <em>${usd(data.totalAmount)}</em></p>\n"
    result += "<p>You earned <em>${data.totalVolumeCredits}</em> credits</p>\n"
    return result
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