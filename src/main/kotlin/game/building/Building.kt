package game.building

import game.product.ProductPile
import game.product.ProductType

interface Building {
    var name : String
    var cost : Double
    val stockPile : MutableMap<ProductType, ProductPile>
    fun tick()
    fun addStockPile(productType: ProductType, number: Int)
    fun removeStockPile(productType: ProductType, number: Int): Int
}