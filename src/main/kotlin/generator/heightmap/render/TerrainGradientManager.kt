package generator.heightmap.render

import java.awt.Color

class TerrainGradientManager : GradientManager() {

    init {
        addGradientPoint(-1.0000f, Color(0, 0, 128)) // deeps
        addGradientPoint(-0.24706f, Color(0, 0, 255)) // shallow
        addGradientPoint(0.00000f, Color(0, 128, 255)) // shore
        addGradientPoint(0.07451f, Color(240, 240, 64)) // sand
        addGradientPoint(0.12941f, Color(32, 160, 0)) // grass
        addGradientPoint(0.38039f, Color(196, 164, 10)) // dirt
        addGradientPoint(0.65490f, Color(128, 128, 128)) // rock
        addGradientPoint(0.85882f, Color(255, 255, 255)) // snow
    }
}