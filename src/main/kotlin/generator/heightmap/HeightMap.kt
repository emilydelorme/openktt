package generator.heightmap

import generator.noise.SimplexNoiseOctave
import kotlin.math.*

class HeightMap(val generationData: GenerationData) {
    var heights: Array<FloatArray> = Array(generationData.width) { FloatArray(generationData.height) }

    init {
        for (i in 0 until generationData.width) {
            heights[i] = FloatArray(generationData.height)
        }
    }

    fun add2DSimplexNoise(generationData: GenerationData) {
        val simplexNoise = SimplexNoiseOctave(generationData)
        val xStart = 0.0
        val yStart = 0.0

        for (i in 0 until generationData.width) {
            for (j in 0 until generationData.height) {
                val x = (xStart + i * ((generationData.largestFeature - xStart) / generationData.width))
                val y = (yStart + j * ((generationData.largestFeature - yStart) / generationData.height))
                heights[i][j] += simplexNoise.getNoise2D(x, y).toFloat()
            }
        }
    }

    fun add3DSimplexNoise(generationData: GenerationData) {
        val simplexNoise = SimplexNoiseOctave(generationData)
        val xStart = 0.0
        val yStart = 0.0
        val dx = generationData.largestFeature - xStart
        val dy = generationData.largestFeature - yStart

        for (x in 0 until generationData.width) {
            for (y in 0 until generationData.height) {
                /* Wikipedia method */

                val r = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))
                val t = atan2(y.toDouble(), x.toDouble())

                val nx= (r * cos(t))
                val ny = ( r * sin(t))
                val nz = t
                /*val s = x.toDouble() / generationData.width
                val t = y.toDouble() / generationData.height

                val nx = (xStart + cos(s * 2 * Math.PI)) * dx / (2 * Math.PI)
                val ny = (yStart + sin(t * 2 * Math.PI)) * dy / (2 * Math.PI)
                val nz = t*/
                //println("nx=$nx ,ny=$ny")*/
                heights[x][y] = simplexNoise.getNoise3D(nx, ny, nz).toFloat()
            }
        }
    }

    fun add4DSimplexNoise(generationData: GenerationData) {
        val simplexNoise = SimplexNoiseOctave(generationData)
        val xStart = 0.0
        val yStart = 0.0
        val dx = generationData.largestFeature - xStart
        val dy = generationData.largestFeature - yStart

        for (x in 0 until generationData.width) {
            for (y in 0 until generationData.height) {
                //Sample noise at smaller intervals
                val s: Double = x.toDouble() / generationData.width
                val t: Double = y.toDouble() / generationData.height
                val nx = xStart + cos(s * 2 * Math.PI) * dx / (2 * Math.PI)
                val ny = yStart + cos(t * 2 * Math.PI) * dy / (2 * Math.PI)
                val nz = xStart + sin(s * 2 * Math.PI) * dx / (2 * Math.PI)
                val nw = yStart + sin(t * 2 * Math.PI) * dy / (2 * Math.PI)

                heights[x][y] += simplexNoise.getNoise4D(nx, ny, nz, nw).toFloat()
            }
        }
    }

    private fun latLonToXYT(lat : Double, lon : Double, coordinates: Coordinates, generationData: GenerationData): Coordinates {
        val r = cos(Math.toRadians(lon))
        coordinates.x = r * cos(Math.toRadians(lat)) * generationData.largestFeature/2
        coordinates.y = sin(Math.toRadians(lon)) * generationData.largestFeature/2
        coordinates.z = r * sin(Math.toRadians(lat)) * generationData.largestFeature/2
        return coordinates
    }

    fun addSphericalNoise(generationData: GenerationData) {
        val heightMap = SimplexNoiseOctave(generationData)

        // Define map area latitude/longitude
        val southLatBound = -180.0
        val northLatBound = 180.0
        val westLonBound = -90.0
        val eastLonBound = 90.0

        var lonExtent = eastLonBound - westLonBound
        var latExtent = northLatBound - southLatBound

        var xDelta = lonExtent / generationData.width
        var yDelta = latExtent / generationData.height

        var curLon = westLonBound
        var curLat = southLatBound

        for(x in 0 until generationData.width) {
            curLon = westLonBound
            for(y in 0 until generationData.height) {
                val coordinates = latLonToXYT(curLat, curLon, Coordinates(0.0, 0.0, 0.0), generationData)

                heights[x][y] = heightMap.getNoise3D(coordinates.x, coordinates.y, coordinates.z).toFloat()

                curLon += xDelta
            }
            curLat += yDelta
        }
    }

    /*
	* What this function does is go through every elements Moore neighbourhood (excluding itself)
	* and look for the lowest point, the match.
	* If the difference between the element and its match is between 0 and a smoothness factor,
	* some of the height will be transferred.
	*/
    fun erode(smoothness: Float) {
        for (i in 1 until generationData.width - 1) {
            for (j in 1 until generationData.height - 1) {
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
                if (0 < dMax && dMax <= smoothness / generationData.width.toFloat()) {
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
        for (i in 1 until generationData.width - 1) {
            for (j in 1 until generationData.height - 1) {
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