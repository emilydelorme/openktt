package main

import com.beust.klaxon.Klaxon
import game.building.factory.Factory
import game.building.factory.RawFactory
import game.building.market.Market
import game.building.market.NationalMarket
import game.country.BasicCountry
import game.flow.BasicFlow
import game.flow.Flow
import game.map.galaxy.BasicGalaxy
import game.map.planet.BasicPlanet
import game.map.planet.Planet
import game.map.region.BasicRegion
import game.map.system.BasicSystem
import game.map.universe.BasicUniverse
import game.product.BasicProduct
import game.product.Product
import game.product.ProductType
import generator.generateFactory
import generator.generateMarket
import generator.generateRoutes
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

public  val productTable : MutableMap<ProductType, Product> = mutableMapOf()

@ExperimentalTime
fun main(args : Array<String>) {

    productTable[ProductType.COAL] = BasicProduct(ProductType.COAL)

    val nobody = BasicCountry("Nobody")
    val japan = BasicCountry("Japan")

    val universe = BasicUniverse(name="Universe", belongTo = nobody)
    val milky = BasicGalaxy(name = "Mily Way", belongTo = nobody)
    val solarSystem = BasicSystem(name = "Solar system", belongTo = japan)
    universe.galaxies.add(milky)
    milky.systems.add(solarSystem)

    val earth = BasicPlanet(name="Earth", belongTo = japan)
    val honshu = BasicRegion(name="Honshū", belongTo = japan)
    solarSystem.planets.add(earth)
    earth.regions.add(honshu)

    // Generator
    val generateFactory: MutableList<Factory> = generateFactory(10000000)
    val generateMarket: MutableList<Market> = generateMarket(1000000)
    val generateRoutes: MutableList<Flow> = generateRoutes(generateFactory, generateMarket, 13000000)
    honshu.building.addAll(generateFactory)
    honshu.building.addAll(generateMarket)

    val nbTicks = 365
    println("Simulating $nbTicks days\n")

    val time = measureTime {
        var totalTime : Duration = Duration.ZERO
        for (i in 0..nbTicks) {
            val timeDay = measureTime {
                honshu.building.stream().parallel().forEach { it.tick() }
                generateRoutes.stream().parallel().forEach { it.tick() }
            }
            totalTime = totalTime.plus(timeDay)
            print("Simulating day $i - ${totalTime.div(i)} / day simulated - Total time: $totalTime\r")
        }
    }
    println("Simulated $nbTicks days in $time")

    Files.writeString(Paths.get("simulationResult.json"), Klaxon().toJsonString(universe))
}