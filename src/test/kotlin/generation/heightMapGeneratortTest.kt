package generation

import generator.heightmap.GenerationData
import generator.heightmap.SimplexHeightMapGenerator
import generator.heightmap.render.GrayScaleGradientManager
import generator.heightmap.render.TerrainGradientManager
import java.io.File
import javax.imageio.ImageIO


fun main(args : Array<String>) {
    generateSample()
}

fun generateSample() {
    val map = SimplexHeightMapGenerator(GenerationData(8196, 142536789, true,
        980892,0.70,0f,0f,8, 30f))
    map.generateHeightMap()
    map.generateBufferedImage(TerrainGradientManager(true))
    println("Min height: ${map.minHeight}, Max height: ${map.maxHeight}")
    ImageIO.write(map.cachedHeightMapImage, "png", File("map.png"))
}