package generation

import generator.heightmap.GenerationData
import generator.heightmap.GenerationDimension
import generator.heightmap.SimplexHeightMapGenerator
import generator.heightmap.render.GrayScaleGradientManager
import generator.heightmap.render.TerrainGradientManager
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.imageio.ImageIO


fun main(args : Array<String>) {
    generateSample()
}

fun generateSample() {
    val gamepath = Paths.get("game")
    gamepath.toFile().mkdirs()

    val generationdata= GenerationData(1024, 1024, (0..Int.MAX_VALUE).random(), GenerationDimension.FOUR_DIMENSION, true,
        10000000,0.75,0f,0f,10, 50f)

    for (i in 2..5) {
        when(i) {
            2 -> generationdata.dimension = GenerationDimension.TWO_DIMENSION
            3 -> generationdata.dimension = GenerationDimension.THREE_DIMENSION
            4 -> generationdata.dimension = GenerationDimension.FOUR_DIMENSION
            5 -> generationdata.dimension = GenerationDimension.SPHERICAL
        }
        val map = SimplexHeightMapGenerator(generationdata)
        map.generateBufferedImage()
        ImageIO.write(map.cachedHeightMapImage, "png", File("game/map-${generationdata.dimension.toString().toLowerCase()}.png"))
    }

}