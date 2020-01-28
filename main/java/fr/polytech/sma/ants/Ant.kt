package fr.polytech.sma.ants

import java.util.*

open class Ant(
    private var _id: UUID = UUID.randomUUID(),
    private var _position: Position
) : Element() {

    var id: UUID
        get() = _id
        set(value) { _id = value }

    var position: Position
        get() = _position
        set(value) { _position = value }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ant) return false

        if (_position != other._position) return false

        return true
    }

    override fun hashCode(): Int {
        return _position.hashCode()
    }
}