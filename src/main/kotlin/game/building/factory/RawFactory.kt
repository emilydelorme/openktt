package game.building.factory

import game.building.BasicBuilding
import game.product.ProductPile
import game.product.ProductType

class RawFactory(
    override var name: String,
    override var cost: Double,
    override val stockPile: MutableMap<ProductType, ProductPile> = mutableMapOf(),
    override var productionRate: Int,
    override val productType: ProductType
) : BasicBuilding(name, cost, stockPile), Factory {

    override fun tick() {
        addStockPile(productType, productionRate)
    }
}