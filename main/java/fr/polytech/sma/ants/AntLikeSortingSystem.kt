package fr.polytech.sma.ants

import java.util.*
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

fun main() {
    val grid = Grid()
    
    // ASYNC
    grid.startAsyncAgents()
    Timer().schedule(100000) {
        grid.stopAsyncAgents()
        Timer().schedule(1000) {
            println("\nResult:")
            println(grid.toString())
            exitProcess(0)
        }
    }
    
    // SYNC
//    grid.startSyncAgents(100000)
//    println("\nResult:")
//    println(grid.toString())
}
