package game.flow

import game.product.ProductType

interface Flow {
    val productType : ProductType
    var speed : Double
    fun tick()
}