package fr.polytech.sma.ants

import java.awt.Color
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame

class View(private var width: Int, private var height: Int, private var grid: Grid) {
	private var f: JFrame = JFrame()
	private var buttons: Array<Array<JButton?>> = Array(width) { arrayOfNulls<JButton>(height) }
	
	init {
		
		for (i in 0 until width) {
			for (j in 0 until height) {
				val gridValue = grid.getAtPos(i, j)
				val b = JButton()
				buttons[i][j] = b
				
				buttons[i][j]!!.background  = when {
					gridValue is Food && gridValue.type == 1 -> Color.RED
					gridValue is Food && gridValue.type == 2 -> Color.BLUE
					gridValue is Ant -> Color.ORANGE
					else -> Color.white
				}
				
				buttons[i][j]!!.isBorderPainted = false
				f.add(b)
			}
		}
		
		f.layout = GridLayout(width, height)
		
		f.setSize(500, 500)
		f.isVisible = true
	}
	
	@Synchronized
	fun setChange(){
		for (i in 0 until width) {
			for (j in 0 until height) {
				val gridValue = grid.getAtPos(i, j)
				
				buttons[i][j]!!.background  = when {
					gridValue is Food && gridValue.type == 1 -> Color.RED
					gridValue is Food && gridValue.type == 2 -> Color.BLUE
					gridValue is Ant -> Color.ORANGE
					else -> Color.white
				}
			}
		}
	}
	
	@Synchronized
	fun move(x: Int, y: Int) {
		val gridValue = grid.getAtPos(x, y)
		this.buttons[x][y]!!.text = when {
			gridValue is Food && gridValue.type == 1 -> "A"
			gridValue is Food && gridValue.type == 2 -> "B"
			gridValue is Ant -> "\uD83D\uDC1C"
			else -> ""
		}
	}
	
	@Synchronized
	fun remove(x: Int, y: Int) {
		this.buttons[x][y]!!.text = ""
	}
	
}