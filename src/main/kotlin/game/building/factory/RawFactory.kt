package game.building.factory

import game.building.Building
import game.product.ProductType

class RawFactory(private val name : String,
                 private var productionRateFactory : Int,
                 private var cost : Double,
                 private var stockpile : Int,
                 private val productTypeFactory: ProductType
              ) : Factory {

    override fun getName(): String {
       return name
    }

    override fun getCost(): Double {
        return cost
    }

    override fun tick() {
        stockpile += productionRateFactory
    }

    override fun getProductionRate(): Int {
        return productionRateFactory
    }

    override fun getStock(): Int {
        return stockpile
    }

    override fun getProductType(): ProductType {
        return productTypeFactory
    }

    override fun toString(): String {
        return "Factory(name='$name', productionRate=$productionRateFactory, cost=$cost, stockpile=$stockpile, productType=$productTypeFactory)"
    }
}