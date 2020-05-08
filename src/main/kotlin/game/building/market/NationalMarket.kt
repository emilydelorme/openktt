package game.building.market

import game.product.ProductType

class NationalMarket(private val name : String,
                     private var cost : Double
        ) : Market {

    override fun getName(): String {
        return name
    }

    override fun getCost(): Double {
        return cost
    }

    override fun tick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}