package game.map.planet

import game.map.Area
import game.map.region.Region

interface Planet : Area {
    val cities : MutableList<Region>
}