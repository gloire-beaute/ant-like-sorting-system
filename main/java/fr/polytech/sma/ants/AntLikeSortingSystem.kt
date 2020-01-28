package fr.polytech.sma.ants

import java.util.*
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

fun main() {
    val grid: Grid = Grid()
    println(grid.toString())
    
    grid.startAgents()
    Timer().schedule(10000) {
        grid.stopAgents()
        Timer().schedule(1000) {
            println("\nResult:")
            println(grid.toString())
            exitProcess(0)
        }
    }
}
