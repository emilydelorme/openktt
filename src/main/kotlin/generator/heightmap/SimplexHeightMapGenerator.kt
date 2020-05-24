package generator.heightmap

import kotlin.random.Random

class SimplexHeightMapGenerator(var generationData: GenerationData) : AbstractHeightMapGenerator(generationData.size, generationData.seed) {

    override fun generateHeightMap(): HeightMap {
        cachedHeightMap = HeightMap(generationData.size, generationData.seed)
        cachedHeightMap.addSimplexNoise(generationData.largestFeature, generationData.persistence)
        //cachedHeightMap.perturb(perturbFrequency, perturbDistance)
        for (i in 0 until generationData.erodeIterations) cachedHeightMap.erode(generationData.erodeSmoothness)
        cachedHeightMap.smooth()

        setMapInfo(cachedHeightMap)
        isValid = false
        return cachedHeightMap
    }

    override fun generateRandomHeightMap() : HeightMap {
        generationData.largestFeature = (0..100).random()
        generationData.persistence = 0.9
        generationData.perturbFrequency = 320.0f
        generationData.perturbDistance = 32.0f
        generationData. erodeIterations = (5..20).random()
        generationData.erodeSmoothness = 160.0f
        return generateHeightMap()
    }

    override fun generateSampleHeightMap(): HeightMap {
        generationData.largestFeature = 100
        generationData.persistence = 0.9
        generationData.perturbFrequency = 320.0f
        generationData.perturbDistance = 32.0f
        generationData. erodeIterations = 20
        generationData.erodeSmoothness = 160.0f
        return this.generateHeightMap()
    }

}