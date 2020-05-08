package game.building

import game.building.factory.RawFactory
import game.product.ProductType

class BuildingFactory {

    companion object {
        fun createBuilding(type: BuildingType) = when(type) {
            BuildingType.FACTORY -> RawFactory("Coal Mine", 2, 0.5, 128, ProductType.COAL)
        }
    }
}