package generator

import game.building.factory.Factory
import game.building.factory.RawFactory
import game.building.market.Market
import game.building.market.NationalMarket
import game.flow.BasicFlow
import game.flow.Flow
import game.product.ProductType
import kotlin.random.Random

fun generateFactory(numberOfFactory: Int): MutableList<Factory> {
    val factoryList: MutableList<Factory> = mutableListOf()
    for (i in 0..numberOfFactory) {
        factoryList.add(RawFactory(i.toString(), 1.0, mutableMapOf(), Random.nextInt(1, 10), ProductType.COAL))
    }
    return factoryList
}

fun generateMarket(numberOfFactory: Int): MutableList<Market> {
    val marketList: MutableList<Market> = mutableListOf()
    for (i in 0..numberOfFactory) {
        marketList.add(
            NationalMarket(
                i.toString(),
                1.0,
                mutableMapOf(),
                mutableMapOf(ProductType.COAL to Random.nextInt(1, 50))
            )
        )
    }
    return marketList
}

fun generateRoutes(factoryList: MutableList<Factory>, marketList: MutableList<Market>, numberOfRoutes: Int): MutableList<Flow> {
    val flowList: MutableList<Flow> = mutableListOf()
    for (i in 0..numberOfRoutes) {
        flowList.add(BasicFlow(ProductType.COAL, Random.nextInt(5, 20), factoryList.random(), marketList.random()))
    }
    return flowList
}