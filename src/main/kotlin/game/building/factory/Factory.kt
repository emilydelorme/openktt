package game.building.factory

import game.building.Building
import game.product.ProductType

interface Factory : Building {
    var productionRate: Int
    val productType: ProductType
}