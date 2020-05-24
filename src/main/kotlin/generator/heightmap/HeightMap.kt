package generator.heightmap

import generator.noise.SimplexNoiseOctave
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class HeightMap(val size : Int, private val seed : Int) {
    var heights: Array<FloatArray> = Array(size) { FloatArray(size) }

    init {
        for (i in 0 until size) {
            heights[i] = FloatArray(size)
        }
    }

    fun add2DSimplexNoise(largestFeature: Int, persistence: Double) {
        val simplexNoise = SimplexNoiseOctave(largestFeature, persistence, seed)
        val xStart = 0.0
        val xEnd = 500.0
        val yStart = 0.0
        val yEnd = 500.0

        for (i in 0 until size) {
            for (j in 0 until size) {
                val x = (xStart + i * ((xEnd - xStart) / size)).toInt()
                val y = (yStart + j * ((yEnd - yStart) / size)).toInt()
                heights[i][j] += simplexNoise.getNoise2D(x, y).toFloat()
            }
        }
    }

    fun add3DSimplexNoise(largestFeature: Int, persistence: Double) {
        val simplexNoise = SimplexNoiseOctave(largestFeature, persistence, seed)
        val xStart = 0.0
        val xEnd = largestFeature
        val yStart = 0.0
        val yEnd = largestFeature
        val dx = xEnd - xStart
        val dy = yEnd - yStart

        for (x in 0 until size) {
            for (y in 0 until size) {
                //Sample noise at smaller intervals
                val s : Double = x.toDouble() / size
                val t: Double =  y.toDouble() / size

                val nx = xStart + cos(s * 2 * Math.PI) * dx / (2*Math.PI)
                val ny = xStart + sin(s * 2 * Math.PI) * dx / (2*Math.PI)
                val nz = t

                heights[x][y] += simplexNoise.getNoise3D(nx, ny, nz).toFloat()
            }
        }
    }

    fun add4DSimplexNoise(largestFeature: Int, persistence: Double) {
        val simplexNoise = SimplexNoiseOctave(largestFeature, persistence, seed)
        val xStart = 0.0
        val xEnd = largestFeature
        val yStart = 0.0
        val yEnd = largestFeature
        val dx = xEnd - xStart
        val dy = yEnd - yStart

        for (x in 0 until size) {

            for (y in 0 until size) {
                //Sample noise at smaller intervals
                val s : Double = x.toDouble() / size
                val t: Double =  y.toDouble() / size

                val nx = xStart + cos(s * 2 * Math.PI) * dx / (2*Math.PI)
                val ny = xStart + cos(t * 2 * Math.PI) * dx / (2*Math.PI)
                val nz = xStart + sin(s * 2 * Math.PI) * dx / (2*Math.PI)
                val nw = yStart + sin(t * 2 * Math.PI) * dy / (2*Math.PI)

                heights[x][y] += simplexNoise.getNoise4D(nx, ny, nz, nw).toFloat()
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