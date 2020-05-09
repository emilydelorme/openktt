package game.map.planet

import game.city.City
import game.country.Country
import game.map.Area
import game.map.region.Region

class BasicPlanet(override val regions: MutableList<Region> = mutableListOf(),
                  override val name: String,
                  override var belongTo: Country
) : Planet {
    override fun toString(): String {
        return "BasicPlanet(regions=$regions, name='$name', belongTo=$belongTo)"
    }
}