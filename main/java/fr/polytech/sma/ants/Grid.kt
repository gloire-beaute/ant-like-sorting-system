package fr.polytech.sma.ants

import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Grid(
    val width: Int = 50,
    val height: Int = 50
) : Observer, Iterable<Ant> {

    companion object {
        const val MOVING_ABILITY = 4
        const val K_PLUS = 0.1f
        const val K_MINUS = 0.3f
        const val NB_AGENTS = 1
        const val NB_A = 200
        const val NB_B = 200
    }

    private val ants: ArrayList<Ant> = ArrayList()
    private val foods: ArrayList<Food> = ArrayList()
    private var numberOfUpdate = 0
    private val rand : Random =  Random(System.getenv("SEED")?.toLong() ?: System.currentTimeMillis())

    init {
        //add A food
        for (i in 0 until NB_A) {
            createFood(1)
        }

        //add B food
        for (i in 0 until NB_B) {
            createFood(2)
        }
        
        //add agents
        for (i in 0 until NB_AGENTS) {
            createAgent()
        }
    }
    
    fun startAsyncAgents() {
        for (agent in ants)
            agent.start()
    }
    
    fun stopAsyncAgents() {
        for (agent in ants)
            agent.stop()
    }
    
    fun startSyncAgents(max_iterations: Int = 1000) {
        for (i in 0 until max_iterations) {
            for (agent in ants)
                agent.act()
        }
    }
    fun startSyncAgents(max_milliseconds: Long = 10000) {
        val begin = System.currentTimeMillis();
        val end = begin + max_milliseconds;
        while (System.currentTimeMillis() < end) {
            for (agent in ants)
                agent.act()
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        numberOfUpdate++
        print("\rNumber of update: $numberOfUpdate")
//        if (o != null && o is Element && numberOfUpdate % 1000 == 0) {
//            print("\n")
//            System.out.flush()
//            print()
//        }
    }

    fun createAgent(): Ant {
        var position = Position(rand.nextInt(width), rand.nextInt(height))
        while (ants.map { a -> a.position }.contains(position)) {
            position = Position(rand.nextInt(width), rand.nextInt(height))
        }
        val ant = Ant(this, UUID.randomUUID(), position, null)
        addAgent(ant)
        return ant
    }

    fun createFood(type: Int): Food {
        var position = Position(rand.nextInt(width), rand.nextInt(height))
        while (foods.map { a -> a.position }.contains(position)) {
            position = Position(rand.nextInt(width), rand.nextInt(height))
        }

        val food = Food(type, position)
        addFood(food)
        return food
    }

    fun addAgent(ant: Ant): Boolean {
        if (ants.map { a -> a.id }.contains(ant.id))
            return false
        if (ants.map { a -> a.position }.contains(ant.position))
            return false

        ant.addObserver(this)
        ants.add(ant)
        return true
    }

    fun addFood(food: Food): Boolean {
        if (foods.map { f -> f.position }.contains(food.position))
            return false

        food.addObserver(this)
        foods.add(food)
        return true
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return !containAnt(x, y) && !containFood(x, y)
    }

    fun containFood(x: Int, y: Int): Boolean {
        val position = Position(x, y)
        return foods.filter { f -> !f.isCarriedByAnt }.map { f -> f.position }.contains(position)
    }

    fun containAnt(x: Int, y: Int): Boolean {
        val position = Position(x, y)
        return (ants.map { f -> f.position }.contains(position))
    }

    fun canMoveAgent(ant: Ant, x: Int, y: Int): Boolean {
        if (x < 0 || width <= x ||
            y < 0 || height <= y ||
            containAnt(x, y))
            return false
        return true
    }

    fun canMoveAgent(ant: Ant, newPosition: Position): Boolean {
        return canMoveAgent(ant, newPosition.x, newPosition.y)
    }

    fun moveAgent(ant: Ant, x: Int, y: Int): Boolean {
        if (!canMoveAgent(ant, x, y))
            return false
        ant.position = Position(x, y)
        return true
    }
    fun moveAgent(ant: Ant, cardinal: Cardinal): Boolean {
        return moveAgent(ant, ant.position + cardinal)
    }
    fun moveAgent(ant: Ant, newPosition: Position): Boolean {
        return moveAgent(ant, newPosition.x, newPosition.y)
    }
    
    fun getAtPos(x: Int, y: Int): Element?{
        val agent = getAgentAtPos(x,y)
        val food = getFoodAtPos(x,y)
        return when {
            agent != null -> agent
            food != null -> food
            else -> null
        }
    }

    fun getAgentAtPos(x: Int, y: Int): Ant? {
        checkRange(x, y)

        for (ant in ants)
            if (ant.position.x == x && ant.position.y == y)
                return ant

        return null
    }
    fun getAgentAtPos(position: Position): Ant? = getAgentAtPos(position.x, position.y)
    
    fun getFoodAtPos(x: Int, y: Int): Food? {
        checkRange(x, y)

        for (food in foods)
            if (food.position.x == x && food.position.y == y)
                return food

        return null
    }
    fun getFoodAtPos(position: Position): Food? = getFoodAtPos(position.x, position.y)

    fun getAgent(index: Int): Ant {
        return ants[index]
    }

    fun getFood(index: Int): Food {
        return foods[index]
    }

    fun size(): Int {
        return ants.size
    }

    operator fun get(x: Int, y: Int): ArrayList<Element> {
        checkRange(x, y)
        
        val elements = ArrayList<Element>()
    
        for (ant in ants)
            if (ant.position.x == x && ant.position.y == y)
                elements.add(ant)
        
        for (food in foods)
            if (food.position.x == x && food.position.y == y)
                elements.add(food)
    
        return elements
    }
    operator fun get(position: Position): ArrayList<Element> = get(position.x, position.y)
    operator fun get(pair: Pair<Int, Int>): ArrayList<Element> = get(pair.first, pair.second)
    operator fun get(element: Element): ArrayList<Element> = get(element.position)
    
    private fun checkRange(x: Int, y: Int) {
        if (x < 0 || width <= x ||
            y < 0 || height <= y)
            throw IndexOutOfBoundsException("($x, $y) is not valid (width=$width, height=$height)")
    }
    private fun checkRange(position: Position) = checkRange(position.x, position.y)
    private fun checkRange(pair: Pair<Int, Int>) = checkRange(pair.first, pair.second)

    override fun iterator(): Iterator<Ant> {
        return ants.iterator()
    }
    
    @Synchronized
    fun print() {
        println(this)
    }

    override fun toString(): String {
        val content = StringBuilder()
        for (y in 0 until width) {
            for (x in 0 until height) {
                var element : String
                element = if(containAnt(x,y)) "🐜"
                else if(containFood(x,y)){
                    val food : Food? = getFoodAtPos(x,y)
                    if(food!!.type == 1) "A" else "B"
                }else " "
                
                content.append(' ')
                    .append(element)
                if (x+1 == height)
                    content.append(' ')
            }
            content.append('\n')
        }

        return content.toString()
    }

}