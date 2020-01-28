import fr.polytech.sma.ants.Ant
import fr.polytech.sma.ants.Element
import fr.polytech.sma.ants.Food
import java.util.*
import kotlin.collections.ArrayList

class Grid(
    val width: Int = 50,
    val height: Int = 50
) : Observer, Iterable<Ant> {

    companion object {
        const val NB_AGENTS = 20
        const val NB_A = 10
        const val NB_B = 10
    }

    private val ants: ArrayList<Ant> = ArrayList()
    private val foods: ArrayList<Food> = ArrayList()

    override fun update(o: Observable?, arg: Any?) {
        if (o != null && o is Element) {
            //print changes
        }
    }

    override fun iterator(): Iterator<Ant> {
        return ants.iterator()
    }

}