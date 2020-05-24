package generator.noise

import java.util.*
import kotlin.math.ceil
import kotlin.math.log10
import kotlin.math.pow

class SimplexNoiseOctave(private val largestFeature: Int, private val persistence: Double, private val seed: Int) {
    private val octaves: Array<SimplexNoiseOptimized>
    private val frequency: DoubleArray
    private val amplitudes: DoubleArray

    init {
        // receives a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        // Math.ceil(7) = 7 = numberOfOctaves
        val numberOfOctaves = ceil(log10(largestFeature.toDouble()) / log10(2.0)).toInt()
        octaves = Array(numberOfOctaves) { SimplexNoiseOptimized(0) }
        frequency = DoubleArray(numberOfOctaves)
        amplitudes = DoubleArray(numberOfOctaves)
        val rnd = Random(seed.toLong())
        for (i in 0 until numberOfOctaves) {
            octaves[i] = SimplexNoiseOptimized(rnd.nextInt())
            frequency[i] = 2.0.pow(i)
            amplitudes[i] = persistence.pow(octaves.size - i)
        }
    }

    fun getNoise2D(x: Int, y: Int): Double {
        var result = 0.0
        for (i in octaves.indices) {
            result += octaves[i].noise(x / frequency[i], y / frequency[i]) * amplitudes[i]
        }
        return result
    }

    fun getNoise3D(x: Double, y: Double, z: Double): Double {
        var result = 0.0
        for (i in octaves.indices) {
            result += octaves[i].noise(x / frequency[i], y / frequency[i], z / frequency[i]) * amplitudes[i]
        }
        return result
    }

    fun getNoise4D(x: Double, y: Double, z: Double, w : Double): Double {
        var result = 0.0
        for (i in octaves.indices) {
            result += octaves[i].noise(
                x / frequency[i],
                y / frequency[i],
                z / frequency[i],
                w / frequency[i]
            ) * amplitudes[i]
        }
        return result
    }
}