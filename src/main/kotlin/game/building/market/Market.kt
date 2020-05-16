package game.building.market

import game.building.Building
import game.product.ProductType

interface Market : Building {
    val consumption: MutableMap<ProductType, Int>
}