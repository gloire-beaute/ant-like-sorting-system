package fr.polytech.sma.ants

class Position(
    var x: Int = 0,
    var y: Int = 0
) {

    companion object {
        fun from(pair: Pair<Int, Int>) = Position(pair)
    }

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)
}