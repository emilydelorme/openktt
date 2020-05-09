package game.map.system

import game.map.Area
import game.map.planet.Planet

interface System : Area {
    val planets : MutableList<Planet>
}