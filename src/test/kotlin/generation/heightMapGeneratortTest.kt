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
    val map = SimplexHeightMapGenerator(GenerationData(600, 1000,
        398588,0.7381108399715283,0f,0f,0, 20f))
    map.generateHeightMap()
    map.generateBufferedImage(TerrainGradientManager())
    println("Min height: ${map.minHeight}, Max height: ${map.maxHeight}")
    ImageIO.write(map.cachedHeightMapImage, "png", File("map.png"))
}