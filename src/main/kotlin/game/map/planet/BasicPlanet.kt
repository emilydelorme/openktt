package game.map.planet

import game.country.Country
import game.map.region.Region

class BasicPlanet(override val name: String,
                  override var belongTo: Country,
                  override val regions: MutableList<Region> = mutableListOf(),
                  override val radius: Double
) : Planet