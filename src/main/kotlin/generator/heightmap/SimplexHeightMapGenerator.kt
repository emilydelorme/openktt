package generator.heightmap

class SimplexHeightMapGenerator(generationData: GenerationData) : AbstractHeightMapGenerator(generationData) {

    override fun generateHeightMap(): HeightMap {
        cachedHeightMap = HeightMap(generationData)
        when (generationData.dimension) {
            GenerationDimension.TWO_DIMENSION -> cachedHeightMap.add2DSimplexNoise(generationData)
            GenerationDimension.THREE_DIMENSION -> cachedHeightMap.add3DSimplexNoise(generationData)
            GenerationDimension.FOUR_DIMENSION -> cachedHeightMap.add4DSimplexNoise(generationData)
        }
        //cachedHeightMap.perturb(perturbFrequency, perturbDistance)
        for (i in 0 until generationData.erodeIterations) cachedHeightMap.erode(generationData.erodeSmoothness)
        cachedHeightMap.smooth()

        setMapInfo(cachedHeightMap)
        isValid = false
        return cachedHeightMap
    }

    override fun generateRandomHeightMap(): HeightMap {
        generationData.largestFeature = (0..100).random()
        generationData.persistence = 0.9
        generationData.perturbFrequency = 320.0f
        generationData.perturbDistance = 32.0f
        generationData.erodeIterations = (5..20).random()
        generationData.erodeSmoothness = 160.0f
        return generateHeightMap()
    }

    override fun generateSampleHeightMap(): HeightMap {
        generationData.seed = 133778
        generationData.largestFeature = 980892
        generationData.persistence = 0.8
        generationData.perturbFrequency = 120f
        generationData.perturbDistance = 32f
        generationData.erodeIterations = 5
        generationData.erodeSmoothness = 20f
        return this.generateHeightMap()
    }

}