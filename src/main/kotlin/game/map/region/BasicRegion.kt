package game.map.region

import game.building.BasicBuilding
import game.building.Building
import game.city.City
import game.country.Country
import game.map.Area

class BasicRegion(
    override val cities: MutableList<City> = mutableListOf(),
    override val building: MutableList<Building> = mutableListOf(),
    override val name: String,
    override var belongTo: Country
) : Region {

}