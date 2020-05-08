package game.map.region

import game.building.Building
import game.city.City
import game.map.Area

interface Region : Area {
    val cities : MutableList<City>
    val building: MutableList<Building>
}