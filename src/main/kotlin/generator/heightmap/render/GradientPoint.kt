package generator.heightmap.render

import java.awt.Color

class GradientPoint(var startHeight: Int, var endColor: Color, var startColor: Color) {

    var endHeight = 255

    constructor(startHeight: Int, endColor: Color): this (startHeight, endColor,  Color(0, 0, 0))

    fun include(point: Int): Boolean {
        return point in startHeight..endHeight
    }

    @Throws(RenderException::class)
    fun getRGBValue(value: Int): Int {
        var r = 0
        var g = 0
        var b = 0
        val startR = startColor.red
        val startG = startColor.green
        val startB = startColor.blue
        val endR = endColor.red
        val endG = endColor.green
        val endB = endColor.blue
        r = offsetRGB(startR, endR, value)
        g = offsetRGB(startG, endG, value)
        b = offsetRGB(startB, endB, value)
        return r shl 16 or (g shl 8) or b
    }

    @Throws(RenderException::class)
    private fun offsetRGB(minRGB: Int, maxRGB: Int, value: Int): Int {
        if (endHeight == startHeight) throw RenderException(
            "a gradient point can not start and end at the same height. "
                    + " Probably you have set the starting point of a GradientPoint to 1"
        )
        return (value - startHeight) * (maxRGB - minRGB) / (endHeight - startHeight) + minRGB
    }

    override fun toString(): String {
        return "GradientPoint(startHeight=$startHeight, endColor=$endColor, startColor=$startColor, endHeight=$endHeight)"
    }
}