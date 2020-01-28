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
				var buttonText: String
				val b = JButton()
				buttonText = when {
					gridValue is Food && gridValue.type == 1 -> "A"
					gridValue is Food && gridValue.type == 2 -> "B"
					gridValue is Food -> "\uD83D\uDC1C"
					else -> ""
				}
				
				buttons[i][j] = b
				buttons[i][j]!!.background = Color.white
				buttons[i][j]!!.text = buttonText
				f.add(b)
			}
		}
		
		f.layout = GridLayout(width, height)
		
		f.setSize(500, 500)
		f.isVisible = true
	}
	
	@Synchronized
	fun move(x: Int, y: Int) {
		val gridValue = grid.getAtPos(x, y)
		this.buttons[x][y]!!.text = when {
			gridValue is Food && gridValue.type == 1 -> "A"
			gridValue is Food && gridValue.type == 2 -> "B"
			gridValue is Food -> "\uD83D\uDC1C"
			else -> ""
		}
	}
	
	@Synchronized
	fun remove(x: Int, y: Int) {
		this.buttons[x][y]!!.text = ""
	}
	
}