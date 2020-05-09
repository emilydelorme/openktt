package main

import com.beust.klaxon.Klaxon
import game.building.factory.RawFactory
import game.building.market.NationalMarket
import game.country.BasicCountry
import game.flow.BasicFlow
import game.map.planet.BasicPlanet
import game.map.planet.Planet
import game.map.region.BasicRegion
import game.product.BasicProduct
import game.product.Product
import game.product.ProductType
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

public  val productTable : MutableMap<ProductType, Product> = mutableMapOf()

fun main(args : Array<String>) {

    productTable[ProductType.COAL] = BasicProduct(ProductType.COAL, 4)

    val japan = BasicCountry("Japan")
    val earth = BasicPlanet(name="Earth", belongTo = japan)
    val honshu = BasicRegion(name="Honshū", belongTo = japan)
    earth.regions.add(honshu)

    val coalFactory = RawFactory(3, ProductType.COAL, "Tokyo Coal Mine", 0.5, mutableMapOf())
    val nationalMarket = NationalMarket("Japan Nationnal Market", 1.0, mutableMapOf())
    honshu.building.add(coalFactory)
    honshu.building.add(nationalMarket)


    val flowCoalMarket = BasicFlow(ProductType.COAL, 2, coalFactory, nationalMarket)

    println("Simulating 50 Ticks")
    for (i in 0..50) {
        coalFactory.tick()
        nationalMarket.tick()
        flowCoalMarket.tick()
    }
    Files.writeString(Paths.get("simulationResult.json"), Klaxon().toJsonString(earth))
}