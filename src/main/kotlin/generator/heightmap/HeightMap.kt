package generator.heightmap

import generator.noise.SimplexNoise
import kotlin.math.abs

class HeightMap(val size : Int, private val seed : Int) {
    var heights: Array<FloatArray> = Array(size) { FloatArray(size) }

    init {
        for (i in 0 until size) {
            heights[i] = FloatArray(size)
        }
    }

    fun addSimplexNoise(largestFeature: Int, persistence: Double) {
        val simplexNoise = SimplexNoise(largestFeature, persistence, seed)
        val xStart = 0.0
        val XEnd = 500.0
        val yStart = 0.0
        val yEnd = 500.0

        for (i in 0 until size) {
            for (j in 0 until size) {
                val x = (xStart + i * ((XEnd - xStart) / size)).toInt()
                val y = (yStart + j * ((yEnd - yStart) / size)).toInt()
                heights[i][j] += simplexNoise.getNoise2D(x, y).toFloat()
            }
        }
    }

    fun setSimplexNoise(
        largestFeature: Int, persistence: Double, xFrom: Int, xTo: Int, yFrom: Int, yTo: Int,
        xNoiseOffset: Int, yNoiseOffset: Int
    ) {
        val simplexNoise = SimplexNoise(largestFeature, persistence, seed)
        val xStart = 0.0
        val XEnd = 500.0
        val yStart = 0.0
        val yEnd = 500.0

        //int xResolution=200;
        //int yResolution=200;

        //double[][] result=new double[xResolution][yResolution];
        for (i in xFrom until xTo) {
            for (j in yFrom until yTo) {
                val x = (xStart + i + xNoiseOffset * ((XEnd - xStart) / size)).toInt()
                val y = (yStart + j + yNoiseOffset * ((yEnd - yStart) / size)).toInt()
                heights[i][j] = simplexNoise.getNoise2D(x, y).toFloat()
            }
        }
    }

    /*
	* What this function does is go through every elements Moore neighbourhood (excluding itself)
	* and look for the lowest point, the match.
	* If the difference between the element and its match is between 0 and a smoothness factor,
	* some of the height will be transferred.
	*/
    fun erode(smoothness: Float) {
        for (i in 1 until size - 1) {
            for (j in 1 until size - 1) {
                var dMax = 0.0f
                val match = intArrayOf(0, 0)
                for (u in -1..1) {
                    for (v in -1..1) {
                        if (abs(u) + abs(v) > 0) {
                            val di = heights[i][j] - heights[i + u][j + v]
                            if (di > dMax) {
                                dMax = di
                                match[0] = u
                                match[1] = v
                            }
                        }
                    }
                }
                if (0 < dMax && dMax <= smoothness / size.toFloat()) {
                    val dH = 0.5f * dMax
                    heights[i][j] -= dH
                    heights[i + match[0]][j + match[1]] += dH
                }
            }
        }
    }

    /*
	* this function will smooth it out a bit.
	*/
    fun smooth() {
        for (i in 1 until size - 1) {
            for (j in 1 until size - 1) {
                var total = 0.0f
                for (u in -1..1) {
                    for (v in -1..1) {
                        total += heights[i + u][j + v]
                    }
                }
                heights[i][j] = total / 9.0f
            }
        }
    }
}