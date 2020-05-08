package game.flow

import game.building.Building
import game.product.BasicProductPile
import game.product.ProductType

class BasicFlow(override val productType: ProductType,
                override var speed: Int,
                override val source: Building,
                override val destination: Building
) : Flow {
    override fun tick() {
        if(source.stockPile[productType]!!.productCount >= speed) {
            source.removeStockPile(productType, speed)
            destination.addStockPile(productType, speed)
        }
    }
}