package generation

import generator.heightmap.GenerationData
import generator.heightmap.GenerationDimension
import generator.heightmap.SimplexHeightMapGenerator
import generator.heightmap.render.GrayScaleGradientManager
import generator.heightmap.render.TerrainGradientManager
import java.io.File
import javax.imageio.ImageIO


fun main(args : Array<String>) {
    generateSample()
}

fun generateSample() {
    val generationdata= GenerationData(1024, 1024, 1000, GenerationDimension.FOUR_DIMENSION, true,
        1000000,0.70,0f,0f,8, 30f)
    for (i in 2..4) {
        when(i) {
            2 -> generationdata.dimension = GenerationDimension.TWO_DIMENSION
            3 -> generationdata.dimension = GenerationDimension.THREE_DIMENSION
            4 -> generationdata.dimension = GenerationDimension.FOUR_DIMENSION
        }
        val map = SimplexHeightMapGenerator(generationdata)
        map.generateBufferedImage()
        ImageIO.write(map.cachedHeightMapImage, "png", File("map-${generationdata.dimension.toString().toLowerCase()}.png"))
    }

}