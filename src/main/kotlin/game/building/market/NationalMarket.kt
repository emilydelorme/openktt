package game.building.market

import game.building.BasicBuilding
import game.product.ProductPile
import game.product.ProductType

class NationalMarket(
    override var name: String,
    override var cost: Double,
    override val stockPile: MutableMap<ProductType, ProductPile>
) : BasicBuilding(name, cost, stockPile), Market {

    override fun tick() { //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "NationalMarket(name='$name', cost=$cost, stockPile=$stockPile)"
    }
}