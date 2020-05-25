package game.building.market

import game.building.BasicBuilding
import game.product.ProductPile
import game.product.ProductType

class NationalMarket(
    override var name: String,
    override var cost: Double,
    override val stockPile: MutableMap<ProductType, ProductPile>,
    override val consumption: MutableMap<ProductType, Int>
) : BasicBuilding(name, cost, stockPile), Market {

    override fun tick() {
        consumption.entries.forEach {
            if (stockPile.containsKey(it.key)) {
                this.removeStockPile(it.key, it.value)
            }
        }
    }

    override fun toString(): String {
        return "NationalMarket(name='$name', cost=$cost, stockPile=$stockPile)\n"
    }
}