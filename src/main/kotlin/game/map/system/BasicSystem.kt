package game.map.system

import game.country.Country
import game.map.planet.Planet

class BasicSystem(
    override val name: String,
    override var belongTo: Country,
    override val planets: MutableList<Planet> = mutableListOf()
) : System {
}