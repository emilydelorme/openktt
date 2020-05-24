package generator.heightmap

import generator.heightmap.render.GradientManager
import generator.heightmap.render.GrayScaleGradientManager
import generator.heightmap.render.TerrainGradientManager
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.abs

abstract class AbstractHeightMapGenerator(var generationData: GenerationData) {

    lateinit var cachedHeightMapImage: BufferedImage
    lateinit var cachedHeightMap: HeightMap
    var isValid = false

    var minHeight: Float = -1.0f
    var maxHeight: Float = 1.0f

    var grayScaleGradientManager = GrayScaleGradientManager(generationData.offSetRGB)
    var terrainGradientManager = TerrainGradientManager(generationData.offSetRGB)
    var defaultGradientManager = terrainGradientManager

    var useGrayScale = false
    var useHSBScale = false

    abstract fun generateHeightMap(): HeightMap
    abstract fun generateRandomHeightMap(): HeightMap
    abstract fun generateSampleHeightMap(): HeightMap

    fun generateBufferedImage(gradientManager: GradientManager): BufferedImage {
        val imageType = if (useGrayScale) BufferedImage.TYPE_BYTE_GRAY else BufferedImage.TYPE_INT_RGB
        cachedHeightMapImage = BufferedImage(cachedHeightMap.size, cachedHeightMap.size, imageType)

        for (i in 0 until cachedHeightMap.size) {
            for (j in 0 until cachedHeightMap.size) {
                cachedHeightMapImage.setRGB(i, j, getColor(cachedHeightMap.heights[i][j], gradientManager))
            }
        }
        isValid = true
        return cachedHeightMapImage
    }


    fun generateBufferedImage(): BufferedImage {
        generateHeightMap()
        return generateBufferedImage(defaultGradientManager)
    }

    private fun getColor(color: Float, gradientManager: GradientManager): Int {
        return if (useHSBScale && !useGrayScale)
            getColorHSB(color)
        else {
            // i'm adding an offset of |min|. My values will go from 0 to max+|min|
            // in this way i can easily  distribute them on the interval [0, 255]
            // minHeight : x = maxHeight : 255
            val value = ((color + abs(minHeight)) * 255 / (maxHeight + abs(minHeight))).toInt()
            gradientManager.renderPoint(value)
        }
    }

    private fun getColorHSB(f: Float): Int {
        val saturation = 0.8f
        val brightness = 0.6f
        //change the [-1,1] f to [0,1] hue
        //I have to use a linear map hue=m*f+q
        val minF = -1f
        val maxF = 1f
        val m = 1 / (maxF - minF)
        val q = -m * minF
        val hue = m * f + q
        return Color.HSBtoRGB(hue, saturation, brightness)
    }

    fun setMapInfo(heightMap: HeightMap) {
        minHeight = 100000f
        maxHeight = -100000f
        for (i in 0 until heightMap.size) {
            for (j in 0 until heightMap.size) {
                if (heightMap.heights[i][j] < minHeight) minHeight = heightMap.heights[i][j]
                if (heightMap.heights[i][j] > maxHeight) maxHeight = heightMap.heights[i][j]
            }
        }
    }

    /**
     * offset heights, to have a range from 0 to maxH+minH
     * distribute those values in the interval from 0 to 2
     * then subtract 1, in order to have them between -1 and 1
     */
    fun normalize() {
        val oMax = 2
        for (i in 0 until generationData.size) {
            for (j in 0 until generationData.size) {
                cachedHeightMap.heights[i][j] = ((cachedHeightMap.heights[i][j] + abs(minHeight) * oMax / (maxHeight + abs(minHeight))) - 1)
            }
        }
        isValid = false
    }

    fun setMaxMinHeight(value: Float) {
        if (value > maxHeight) maxHeight = value
        if (value < minHeight) minHeight = value
    }

}