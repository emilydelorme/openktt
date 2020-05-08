package game.map

import game.country.Country

interface Area {
    val name : String
    var belongTo: Country
}