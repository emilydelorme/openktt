package main

import game.building.factory.RawFactory
import game.building.market.NationalMarket
import game.product.BasicProduct
import game.product.Product
import game.product.ProductType

fun main(args : Array<String>) {

    val productTable : MutableMap<ProductType, Product> = mutableMapOf()
    productTable[ProductType.COAL] = BasicProduct(ProductType.COAL, 4)

    val coalFactory = RawFactory("Coal Mine", 4, 0.5, 128, ProductType.COAL)
    val nationalMarket = NationalMarket("Tokyo Market", 0.25)

    println("Simulating 100 Ticks")
    for (i in 0..100) {
        coalFactory.tick()
        nationalMarket.tick()
    }
    println(coalFactory)
    println("Value produced: ${coalFactory.getStock() * productTable[coalFactory.getProductType()]!!.getPrice() }€")
    println("Value produced: ${coalFactory.getStock() * productTable[coalFactory.getProductType()]!!.getPrice() }€")
}