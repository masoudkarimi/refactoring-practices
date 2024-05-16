package ir.masoudkarimi.ir.masoudkarimi.chapter1

open class Performance(
    val playId: String,
    val audience: Int
)

data class Invoice(
    val customer: String,
    val performances: List<Performance>
)

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
    )
)