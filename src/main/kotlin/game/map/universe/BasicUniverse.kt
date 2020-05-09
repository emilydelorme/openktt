package game.map.universe

import game.country.Country
import game.map.galaxy.Galaxy

class BasicUniverse(
    override val name: String,
    override var belongTo: Country,
    override val univers: MutableList<Universe> = mutableListOf(),
    override val galaxies: MutableList<Galaxy> = mutableListOf()
) : Universe