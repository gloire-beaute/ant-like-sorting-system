package fr.polytech.sma.ants

import java.util.*

open class Element(
	private var _position: Position = Position()
) : Observable() {
	var position: Position
		get() = _position
		set(value) {
			_position = value
			setChanged()
			notifyObservers()
		}
}