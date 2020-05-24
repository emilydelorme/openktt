package generator.heightmap.render

import java.awt.Color

open class GradientManager {
    private val gradientPoints: MutableList<GradientPoint> = mutableListOf()

    /**
     * @param height must be a value between -1 and 1. It represents the height from which this color will be applied
     * @param color represents the color that will be applied
     * @throws RenderException if height  is not between -1 and 1
     */
    @Throws(RenderException::class)
    fun addGradientPoint(height: Float, color: Color?) {
        if (height < -1 || height > 1) throw RenderException("the height of a GrandientPoint must be >= -1 and <= 1")

        // since i'm working with RGB, internally i'm using heights from 0 to 255
        val rgbHeight = ((height + 1) * 255 / 2).toInt()
        if (gradientPoints.isEmpty()) {
            gradientPoints.add(GradientPoint(rgbHeight, color!!))
        } else {
            // sets the endHeight of the previous color to the startHeight of the new color -1
            gradientPoints[gradientPoints.size - 1].endHeight = rgbHeight - 1
            // create a new GradientPoint, with a starting color == to the endColor of the previous GradientPoint
            gradientPoints.add(GradientPoint(rgbHeight, color!!, gradientPoints[gradientPoints.size - 1].endColor))
        }
    }

    fun clearGradientPoints() {
        gradientPoints.clear()
    }

    /**
     * If a point has no interval the method returns black
     *
     * @param point is expected to be a value from 0 to be 255
     * @throws RenderException
     */
    @Throws(RenderException::class)
    fun renderPoint(point: Int): Int {
        var fixedPoint = point
        when {
            fixedPoint > 255 -> fixedPoint = 255
            fixedPoint < 0 -> fixedPoint = 0
        }

        for (i in gradientPoints.indices) {
            if (gradientPoints[i].include(fixedPoint)) return gradientPoints[i].getRGBValue(fixedPoint)
        }
        // Return black point if point not in the interval (Integer color)
        return 0 shl 16 or (0 shl 8) or 0
    }

    fun isEmpty(): Boolean {
        return gradientPoints.isEmpty()
    }

    override fun toString(): String {
        return "GradientManager(gradientPoints=$gradientPoints)"
    }
}