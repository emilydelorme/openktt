package generator.heightmap.render

import java.awt.Color

class GradientPoint(var startHeight: Int, var endColor: Color, var startColor: Color) {

    // Default end height is 255
    var endHeight = 255

    constructor(startHeight: Int, endColor: Color) : this(startHeight, endColor, Color(0, 0, 0))

    /**
     *
     *
     * @param point
     * @return true if the point is in the inverteval
     */
    fun include(point: Int): Boolean {
        return point in startHeight..endHeight
    }

    /**
     * Give the RGB value
     *
     * @param value Color represented with an Int
     * @return
     */
    fun getRGBValue(value: Int): Int {
        return (endColor.red shl 16) or
                (endColor.green shl 8) or
               endColor.blue
    }

    /**
     * Give the RGB value in int with an offset according the base rgb color
     *
     * @param value Color represented with an Int
     * @return
     */
    @Throws(RenderException::class)
    fun getRGBValueWithOffset(value: Int): Int {
        return (offsetRGB(startColor.red, endColor.red, value) shl 16) or
                (offsetRGB(startColor.green, endColor.green, value) shl 8) or
                offsetRGB(startColor.blue, endColor.blue, value)
    }

    @Throws(RenderException::class)
    private fun offsetRGB(minRGB: Int, maxRGB: Int, value: Int): Int {
        if (endHeight == startHeight)
            throw RenderException(
                "a gradient point can not start and end at the same height. "
                        + " Probably you have set the starting point of a GradientPoint to 1"
            )
        return (value - startHeight) * (maxRGB - minRGB) / (endHeight - startHeight) + minRGB
    }

    override fun toString(): String {
        return "GradientPoint(startHeight=$startHeight, endColor=$endColor, startColor=$startColor, endHeight=$endHeight)"
    }
}