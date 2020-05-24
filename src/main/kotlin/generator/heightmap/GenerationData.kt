package generator.heightmap

data class GenerationData(var size : Int,
                          var seed : Int,
                          var offSetRGB : Boolean,
                          var largestFeature : Int,
                          var persistence : Double,
                          var perturbFrequency : Float,
                          var perturbDistance : Float,
                          var erodeIterations : Int,
                          var erodeSmoothness : Float
)