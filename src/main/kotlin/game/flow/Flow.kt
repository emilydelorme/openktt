package game.flow

import game.building.Building
import game.product.ProductType

interface Flow {
    val productType : ProductType
    var speed : Int
    val source : Building
    val destination : Building

    fun tick()
}