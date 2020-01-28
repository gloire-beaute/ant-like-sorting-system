package fr.polytech.sma.ants

import java.lang.IndexOutOfBoundsException
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class Position(
	var x: Int = 0,
	var y: Int = 0
) {
	
	companion object {
		fun from(pair: Pair<Int, Int>) = Position(pair)
	}
	
	constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)
	constructor(position: Position) : this(position.x, position.y)
	
	fun toPair(): Pair<Int, Int> = Pair(x, y)
	
	//region OPERATORS OVERLOADING
	
	operator fun unaryPlus(): Position = this
	
	operator fun unaryMinus(): Position = Position(-this.x, -this.y)
	
	operator fun inc(): Position = Position(this.x+1, this.y+1)
	
	operator fun dec(): Position = Position(this.x-1, this.y-1)
	
	operator fun plus(position: Position): Position = Position(this.x + position.x, this.y + position.y)
	operator fun plus(cardinal: Cardinal): Position = this + cardinal.position
	
	operator fun minus(position: Position): Position = Position(this.x - position.x, this.y - position.y)
	operator fun minus(cardinal: Cardinal): Position = this - cardinal.position
	
	operator fun times(position: Position): Position = Position(this.x * position.x, this.y * position.y)
	operator fun times(cardinal: Cardinal): Position = this * cardinal.position
	
	operator fun div(position: Position): Position = Position(this.x / position.x, this.y /position.y)
	operator fun div(cardinal: Cardinal): Position = this/cardinal.position
	
	operator fun rem(position: Position): Position = Position(this.x % position.x, this.y % position.y)
	operator fun rem(cardinal: Cardinal): Position = this % cardinal.position
	
	operator fun contains(pos: Int): Boolean = pos == x || pos == y
	
	operator fun get(i: Int): Int {
		if (i != 0 && i != 1)
			throw IndexOutOfBoundsException("$i is not 0 or 1.")
		
		return if (i == 0) x else y
	}
	
	operator fun set(i: Int, value: Int) {
		if (i != 0 && i != 1)
			throw IndexOutOfBoundsException("$i is not 0 or 1.")
		
		if (i == 0)
			x = value
		else
			y = value
	}
	
	operator fun plusAssign(position: Position) {
		this.x += position.x
		this.y += position.y
	}
	
	operator fun minusAssign(position: Position) {
		this.x -= position.x
		this.y -= position.y
	}
	
	operator fun timesAssign(position: Position) {
		this.x *= position.x
		this.y *= position.y
	}
	
	operator fun divAssign(position: Position) {
		this.x /= position.x
		this.y /= position.y
	}
	
	operator fun remAssign(position: Position) {
		this.x %= position.x
		this.y %= position.y
	}
	
	operator fun compareTo(position: Position): Int {
		return sqrt((this.x - position.x).toDouble().pow(2.0) + (this.y - position.y).toDouble().pow(2.0)).roundToInt()
	}
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Position) return false
		
		if (x != other.x) return false
		if (y != other.y) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		return result
	}
	
	//endregion
}