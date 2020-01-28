package fr.polytech.sma.ants

import kotlin.random.Random

enum class Cardinal(
	val position: Position
) {
	CENTER(0, 0),
	NORTH(0, -1),
	EAST(1, 0),
	SOUTH(0, 1),
	WEST(-1, 0);
	
	companion object {
		fun pickRandomly(random: Random, exceptCenter: Boolean = true): Cardinal {
			val list = ArrayList<Cardinal>(Cardinal.values().toList())
			if (exceptCenter)
				list.remove(CENTER)
			return list[random.nextInt(0, list.size)]
		}
	}
	
	constructor(x: Int, y: Int) : this(Position(x, y))
	
	fun invert(): Cardinal {
		return when (this) {
			CENTER -> CENTER
			NORTH -> SOUTH
			EAST -> WEST
			SOUTH -> NORTH
			WEST -> EAST
		}
	}
	
	fun turnClockwise(): Cardinal {
		return when (this) {
			CENTER -> CENTER
			NORTH -> EAST
			EAST -> SOUTH
			SOUTH -> WEST
			WEST -> NORTH
		}
	}
	
	fun turnAnticlockwise(): Cardinal = turnClockwise().invert()
	
	operator fun plus(cardinal: Cardinal): Position {
		return this.position + cardinal.position
	}
	operator fun plus(position: Position): Position {
		return this.position + position
	}
	
	operator fun minus(cardinal: Cardinal): Position {
		return this.position - cardinal.position
	}
	operator fun minus(position: Position): Position {
		return this.position - position
	}
	
	operator fun times(cardinal: Cardinal): Position {
		return this.position * cardinal.position
	}
	operator fun times(position: Position): Position {
		return this.position * position
	}
	
	operator fun div(cardinal: Cardinal): Position {
		return this.position/cardinal.position
	}
	operator fun div(position: Position): Position {
		return this.position/position
	}
	
	operator fun not(): Cardinal = invert()
}