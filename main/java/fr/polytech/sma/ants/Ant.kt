package fr.polytech.sma.ants

import java.util.*

open class Ant(
    private val _id: UUID = UUID.randomUUID(),
    private val _position: Position
) : Element() {
}