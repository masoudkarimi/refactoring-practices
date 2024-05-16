# Refactoring Exercise from "Refactoring" book by Martin Fowler - Chapter 1

## Overview

This repository contains the initial and refactored versions of a code snippet from Chapter 1 of Martin Fowler's book 
"Refactoring: Improving the Design of Existing Code". The code calculates and generates a statement for a theater 
company based on invoice data and play information. The refactoring process includes writing unit tests to ensure that 
changes do not break existing functionality and applying various refactoring techniques to improve the code's structure 
and readability.

## [Initial Code - branch chapter1-start](https://github.com/masoudkarimi/refactoring-practices/tree/chapter1-start)

The initial implementation of the `statement` function calculates the total amount and volume credits for each 
performance in an invoice, formats the results into a string, and returns the statement. Here is the original code:

```kotlin
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
```

## [Refactored Code - branch chapter1-end](https://github.com/masoudkarimi/refactoring-practices/tree/chapter1-end)

The refactored version of the code utilizes various refactoring techniques such as Function Extraction, Inline Variable, Split Loop, Replace Loop with Pipeline, and Replace Conditional with Polymorphism. Here are the key changes:

1. **[Function Extraction](https://refactoring.guru/extract-method)**: Extracted code into smaller functions to improve readability and maintainability.
2. **[Inline Temp](https://refactoring.guru/inline-temp)**: Simplified variable declarations.
3. **[Replace Temp with Query](https://refactoring.guru/replace-temp-with-query)**
4. **[Split Loop](https://refactoring.com/catalog/splitLoop.html)**: Split different parts of a long loop into multiple smaller loops, so that you can extract it
5. **[Replace Loop with Pipeline](https://refactoring.com/catalog/replaceLoopWithPipeline.html)**: Used higher-order functions to process collections.
6. **[Replace Conditional with Polymorphism](https://refactoring.guru/replace-conditional-with-polymorphism)**: Used polymorphism to handle different play types.

### Key Refactored Components

- [Statement.kt](src/main/kotlin/ir/masoudkarimi/chapter1/Statement.kt)
- [StatementData.kt](src/main/kotlin/ir/masoudkarimi/chapter1/StatementData.kt)

## [Unit Tests](src/test/kotlin/ir/masoudkarimi/chapter1/Chapter1StatementTest.kt)

Unit tests were written to ensure that the refactoring did not break existing functionality. These tests validate the 
correctness of both the initial and refactored versions of the code. The tests cover different scenarios and edge cases
for generating statements.

## Conclusion

Refactoring the initial implementation improved the code's readability, maintainability, and scalability by applying 
well-known refactoring techniques. These changes demonstrate how systematic refactoring can enhance code quality without 
altering its behavior.

For more information on the specific techniques used and their benefits, refer to Martin Fowler's "Refactoring" book.

![Refactoring Book Cover](/image/Refactoring.jpg)

