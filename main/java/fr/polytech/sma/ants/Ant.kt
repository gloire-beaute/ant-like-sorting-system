package fr.polytech.sma.ants

import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

open class Ant(
	private var _grid: Grid,
	private var _id: UUID = UUID.randomUUID(),
	private var _position: Position,
	private var _food: Food? = null
) : Element(_position), Runnable {
	
	companion object {
		const val MEMORY_CAPACITY = 10
	}
	
	private var thread: Thread? = null
	private var threadBool: AtomicBoolean = AtomicBoolean(true)
	
	val grid: Grid
		get() = _grid
	
	var id: UUID
		get() = _id
		set(value) { _id = value }
	
	private val memory: ArrayList<Int> = ArrayList(MEMORY_CAPACITY)
	
	//region MEMORY METHODS
	
	fun addMemory(event: Int?) {
		while (memory.size >= MEMORY_CAPACITY)
			memory.remove(0)
		
		memory.add(event ?: 0)
	}
	fun addMemory(food: Food?) {
		if (food == null)
			addMemory(0)
		else {
			if (food.type == 0)
				throw NumberFormatException("Food \"$food\" has type ${food.type}, which is not valid.")
			addMemory(food.type)
		}
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
	
	//region FOOD METHODS
	
	fun isCarryingFood(): Boolean = _food != null
	
	@Deprecated("The instance of `Food` needs to be processed when exiting an Ant. This function doesn't do this process.", ReplaceWith("popFood()"))
	fun getFood(): Food? = _food
	
	fun popFood(): Food? {
		if (_food == null)
			return null
		
		val food = Food(_food!!)
		food.isCarriedByAnt = false
		food.position = this.position
		_food = null
		return food
	}
	
	@Deprecated("The instance of `Food` needs to be processed when entering an Ant. This function doesn't do this process.", ReplaceWith("carryFood()"))
	fun setFood(food: Food?) {
		_food = food
	}
	
	fun carryFood(food: Food) {
		if (_food != null)
			throw IllegalStateException("The ant is already carrying food!\n\tFood being carried=$_food\n\tFood to carry=$food\n\tAgent=$this")
		
		food.isCarriedByAnt = true
		_food = food
	}
	
	//endregion
	
	//region ACTIONS METHODS
	
	/**
	 * Make ant moves if possible in the grid.
	 */
	fun move(cardinal: Cardinal): Boolean = grid.moveAgent(this, cardinal)
	
	/**
	 * Detect what is on the same cell of the ant. Is there food or nothing? If it's food, what's its type? Any event
	 * is then saved in its memory.
	 * @param saveInMemory If `true`, save the event in the memory (even if nothing was found). If `false`, don't change
	 * the memory, and just return what's on the cell.
	 * @return Return the detected element. If none were found, return `null`.
	 */
	fun detect(saveInMemory: Boolean = true): Element? {
		val food: Food? = grid.getFoodAtPos(this.position)
		
		// Save event in memory if wanted
		if (saveInMemory)
			addMemory(food)
		
		return food
	}
	
	//endregion
	
	//region THREAD METHODS
	
	fun start() {
		if (thread == null || thread?.isAlive == true) {
			thread = thread {
				run()
			}
		}
	}
	fun stop() {
		threadBool.set(false)
		thread?.join(500)
	}
	
	override fun run() {
		threadBool.set(true)
		var iter = 0
		while (threadBool.get()) {
			//
			iter++
		}
	}
	
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
	
	override fun toString(): String {
		return "Ant(_grid=$_grid, _id=$_id, _position=$_position, _food=$_food, thread=$thread, threadBool=$threadBool, memory=$memory)"
	}
	
	//endregion
}