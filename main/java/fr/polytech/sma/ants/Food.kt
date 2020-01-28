package fr.polytech.sma.ants


open class Food(
    private var _type: Int, // 1 == A, 2 == B
    private var _position: Position,
    private var _isCarriedByAnt: Boolean = false
) : Element(_position) {

    var type: Int
        get() = _type
        set(value) { _type = value }
    
    var isCarriedByAnt: Boolean
        get() = _isCarriedByAnt
        set(value) { _isCarriedByAnt = value }
    
    constructor(food: Food) : this(food.type, food.position)
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Food) return false
        
        if (_type != other._type) return false
        if (_position != other._position) return false
        if (_isCarriedByAnt != other._isCarriedByAnt) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = _type
        result = 31 * result + _position.hashCode()
        result = 31 * result + _isCarriedByAnt.hashCode()
        return result
    }
}