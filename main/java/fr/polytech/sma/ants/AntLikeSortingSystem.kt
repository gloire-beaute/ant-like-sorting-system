package fr.polytech.sma.ants

import java.util.*
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

fun main() {
    val grid: Grid = Grid()
    println(grid.toString())
    
    // ASYNC
//    grid.startAsyncAgents()
//    Timer().schedule(10000) {
//        grid.stopAsyncAgents()
//        Timer().schedule(1000) {
//            println("\nResult:")
//            println(grid.toString())
//            exitProcess(0)
//        }
//    }
    
    // SYNC
    grid.startSyncAgents(1000)
}
