package game.map.universe

import game.map.Area
import game.map.galaxy.Galaxy

interface Universe : Area {
    val univers : MutableList<Universe>
    val galaxies : MutableList<Galaxy>
}