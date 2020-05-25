package generator.heightmap

data class GenerationData(var width : Int,
                          var height: Int,
                          var seed : Int,
                          var dimension: GenerationDimension,
                          var offSetRGB : Boolean,
                          var largestFeature : Int,
                          var persistence : Double,
                          var perturbFrequency : Float,
                          var perturbDistance : Float,
                          var erodeIterations : Int,
                          var erodeSmoothness : Float
)