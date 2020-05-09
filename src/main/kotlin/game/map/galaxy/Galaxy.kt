package game.map.galaxy

import game.map.Area
import game.map.system.System

interface Galaxy : Area {
    val systems : MutableList<System>
}