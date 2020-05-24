package generator.heightmap.render

import java.awt.Color

class GrayScaleGradientManager(offsetRGB: Boolean) : GradientManager(offsetRGB) {

    init {
        addGradientPoint(-1f, Color(255, 255, 255))
    }
}