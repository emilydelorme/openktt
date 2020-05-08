package game.building

import game.product.BasicProductPile
import game.product.ProductPile
import game.product.ProductType
import main.productTable

abstract class BasicBuilding(
    override var name: String,
    override var cost: Double,
    override val stockPile: MutableMap<ProductType, ProductPile>
) : Building {

    override fun addStockPile(productType: ProductType, number: Int) {
        if (stockPile.containsKey(productType)) {
            if (stockPile[productType]!!.productCount + number > stockPile[productType]!!.maximum) {
                stockPile[productType]!!.productCount = stockPile[productType]!!.maximum
            } else {
                stockPile[productType]!!.productCount += number
            }
        } else {
            stockPile[productType] = BasicProductPile(128, number, productType, productTable[productType]!!.price)
        }
    }

    override fun removeStockPile(productType: ProductType, number: Int): Int {
        val removePile = if (stockPile[productType]!!.productCount - number < 0)
            stockPile[productType]!!.productCount
        else
            number
        stockPile[productType]!!.productCount -= removePile
        return removePile
    }
}