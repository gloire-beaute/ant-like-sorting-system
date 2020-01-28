package fr.polytech.sma.ants

import java.util.*
import kotlin.collections.ArrayList

open class Ant(
	private var _id: UUID = UUID.randomUUID(),
	private var _position: Position
) : Element() {
	
	companion object {
		const val MEMORY_CAPACITY = 10
	}
	
	var id: UUID
		get() = _id
		set(value) { _id = value }
	
	var position: Position
		get() = _position
		set(value) { _position = value }
	
	private val memory: ArrayList<Int> = ArrayList(MEMORY_CAPACITY)
	
	//region MEMORY METHODS
	
	fun addMemory(event: Int?) {
		while (memory.size >= MEMORY_CAPACITY)
			memory.remove(0)
		
		memory.add(event ?: 0)
	}
	
	fun getMemory(index: Int): Int = memory[index]
	fun setMemory(index: Int, value: Int) {
		memory[index] = value
	}
	
	fun removeMemoryAt(index: Int): Int = memory.removeAt(index)
	fun removeMemoryFirst(event: Int): Boolean = memory.remove(event)
	
	fun memoryIterator(): Iterator<Int> = memory.iterator()
	
	fun memorySize(): Int = memory.size
	
	fun getNumberOf(event: Int): Int {
		var counter = 0
		for (mem in memory)
			if (mem == event)
				counter++
		return counter
	}
	
	fun getProportionOf(event: Int): Double = getNumberOf(event).toDouble()/memorySize().toDouble()
	
	operator fun contains(event: Int): Boolean = memory.contains(event)
	
	operator fun get(index: Int): Int = getMemory(index)
	operator fun set(index: Int, value: Int) = setMemory(index, value)
	
	operator fun invoke(event: Int): Double = getProportionOf(event)
	
	//endregion
	
	//region OVERRIDES
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Ant) return false
		
		if (_position != other._position) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		return _position.hashCode()
	}
	
	//endregion
}