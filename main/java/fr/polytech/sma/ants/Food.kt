package fr.polytech.sma.ants


open class Food(
    private var _type: Int, //0 == A, 1 == B
    private var _position: Position
) : Element() {

    var position: Position
        get() = _position
        set(value) { _position = value }

    var type: Int
        get() = _type
        set(value) { _type = value }
}