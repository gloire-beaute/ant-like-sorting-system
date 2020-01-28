import fr.polytech.sma.ants.Ant
import fr.polytech.sma.ants.Element
import fr.polytech.sma.ants.Food
import fr.polytech.sma.ants.Position
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Grid(
    val width: Int = 50,
    val height: Int = 50
) : Observer, Iterable<Ant> {

    companion object {
        const val NB_AGENTS = 20
        const val NB_A = 200
        const val NB_B = 200
    }

    private val ants: ArrayList<Ant> = ArrayList()
    private val foods: ArrayList<Food> = ArrayList()

    init {
        //add A food
        for (i in 0 until NB_A) {
            createFood(1)
        }

        //add B food
        for (i in 0 until NB_B) {
            createFood(2)
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o != null && o is Element) {
            //print changes
        }
    }

    fun createAgent(): Ant {
        var position = Position(Random.nextInt(width), Random.nextInt(height))
        while (ants.map { a -> a.position }.contains(position)) {
            position = Position(Random.nextInt(width), Random.nextInt(height))
        }
        val ant = Ant(UUID.randomUUID(), position)
        addAgent(ant)
        return ant
    }

    fun createFood(type: Int): Food {
        var position = Position(Random.nextInt(width), Random.nextInt(height))
        while (foods.map { a -> a.position }.contains(position)) {
            position = Position(Random.nextInt(width), Random.nextInt(height))
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
        return true;
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return !containAnt(x, y) && !containFood(x, y)
    }

    fun containFood(x: Int, y: Int): Boolean {
        val position = Position(x, y)
        if (foods.map { f -> f.position }.contains(position))
            return true

        return false
    }

    fun containAnt(x: Int, y: Int): Boolean {
        val position = Position(x, y)
        if (ants.map { f -> f.position }.contains(position))
            return true

        return false
    }

    fun canMoveAgent(ant: Ant, x: Int, y: Int): Boolean {
        if (containAnt(x, y))
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

    fun moveAgent(ant: Ant, newPosition: Position): Boolean {
        return moveAgent(ant, newPosition.x, newPosition.y)
    }

    fun getAgentAtPos(x: Int, y: Int): Ant? {
        if (x < 0 || width <= x ||
            y < 0 || height <= y
        )
            throw IndexOutOfBoundsException("($x, $y) is not valid (width=$width, height=$height)")

        for (ant in ants)
            if (ant.position.x == x && ant.position.y == y)
                return ant

        return null
    }

    fun getAgent(index: Int): Ant {
        return ants[index]
    }

    fun size(): Int {
        return ants.size
    }

    operator fun get(index: Int): Ant {
        return getAgent(index)
    }

    operator fun get(x: Int, y: Int): Ant? {
        return getAgentAtPos(x, y)
    }

    override fun iterator(): Iterator<Ant> {
        return ants.iterator()
    }

}