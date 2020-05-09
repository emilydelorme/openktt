package game.building.factory

import game.building.BasicBuilding
import game.product.ProductPile
import game.product.ProductType

class RawFactory(
    override var productionRate: Int,
    override val productType: ProductType,
    override var name: String,
    override var cost: Double,
    override val stockPile: MutableMap<ProductType, ProductPile>
) : BasicBuilding(name, cost, stockPile), Factory {

    override fun tick() {
        addStockPile(productType, productionRate)
    }

    override fun toString(): String {
        return "RawFactory(productionRate=$productionRate, productType=$productType, name='$name', cost=$cost, stockPile=$stockPile)\n"
    }
}