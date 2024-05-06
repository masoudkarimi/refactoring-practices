package ir.masoudkarimi.ir.masoudkarimi.chapter1

data class Play(
    val name: String,
    val type: String
)

typealias Plays = Map<String, Play>

val plays = mapOf(
    "hamlet" to Play(name = "Hamlet", type = "tragedy"),
    "as-like" to Play(name = "As You Like It", type = "comedy"),
    "othello" to Play(name = "Othello", type = "tragedy")
)

