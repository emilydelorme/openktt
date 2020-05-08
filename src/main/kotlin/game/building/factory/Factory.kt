package game.building.factory

import game.building.Building
import game.product.ProductType

interface Factory : Building {
    fun getProductionRate() : Int
    fun getStock() : Int
    fun getProductType() : ProductType
}