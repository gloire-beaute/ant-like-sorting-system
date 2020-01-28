package fr.polytech.sma.ants


open class Food(
    private var _type: Int, // 1 == A, 2 == B
    private var _position: Position
) : Element() {

    var position: Position
        get() = _position
        set(value) { _position = value }

    var type: Int
        get() = _type
        set(value) { _type = value }
}