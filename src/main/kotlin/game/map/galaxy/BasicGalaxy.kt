package game.map.galaxy

import game.country.Country
import game.map.system.System

class BasicGalaxy(
    override val name: String,
    override var belongTo: Country,
    override val systems: MutableList<System> = mutableListOf()
) : Galaxy