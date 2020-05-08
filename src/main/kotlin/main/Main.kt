package main

import game.building.factory.RawFactory
import game.building.market.NationalMarket
import game.flow.BasicFlow
import game.product.BasicProduct
import game.product.Product
import game.product.ProductType

public  val productTable : MutableMap<ProductType, Product> = mutableMapOf()

fun main(args : Array<String>) {


    productTable[ProductType.COAL] = BasicProduct(ProductType.COAL, 4)

    val coalFactory = RawFactory(3, ProductType.COAL, "Tokyo Coal Mine", 0.5, mutableMapOf())
    val nationalMarket = NationalMarket("Japan Nationnal Market", 1.0, mutableMapOf())
    val flowCoalMarket = BasicFlow(ProductType.COAL, 2, coalFactory, nationalMarket)

    println("Simulating 50 Ticks")
    for (i in 0..50) {
        coalFactory.tick()
        nationalMarket.tick()
        flowCoalMarket.tick()
    }
    println(coalFactory)
    println(nationalMarket)
}