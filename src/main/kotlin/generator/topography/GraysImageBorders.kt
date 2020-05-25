package generator.topography

import java.awt.Color
import java.awt.image.BufferedImage


fun getBordersImage(image: BufferedImage, threshold: Int): BufferedImage {
    val height = image.height
    val width = image.width
    val greysIntMatrix: Array<IntArray> = getGreysIntMatrix(image)
    //modules of the "gradient" vector
    val differentialIntMatrix: Array<IntArray> =
        getDifferentialIntMatrix(greysIntMatrix, width, height)
    val imageBorders = BufferedImage(width - 1, height - 1, BufferedImage.TYPE_INT_ARGB)
    drawBordersBlackAndWhite(imageBorders, differentialIntMatrix, width, height, threshold)
    return imageBorders
}

//I get a gray-scale image by averaging the RGB components, and setting every output RGB component (in each pixel) to the found average
fun getGreysImage(image: BufferedImage): BufferedImage {
    val height = image.height
    val width = image.width
    val greysIntMatrix: Array<IntArray> = getGreysIntMatrix(image)
    val greysImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    for (i in 0 until width) {
        for (j in 0 until height) {
            val grey =
                Color(greysIntMatrix[i][j], greysIntMatrix[i][j], greysIntMatrix[i][j])
            greysImage.setRGB(i, j, grey.rgb)
        }
    }
    return greysImage
}

//get an image in which the grey tonality is blacker where the module of the gradient is more pronounced
//OPPOSITE BEHAVIOUR!!
fun getGreyGradientModuleImage(image: BufferedImage): BufferedImage {
    val height = image.height
    val width = image.width
    val colorIntMatrix: Array<IntArray> = getColorIntMatrix(image)
    val greysIntMatrix: Array<IntArray> = getGreysIntMatrix(colorIntMatrix, width, height)
    //modules of the "gradient" vector
    val differentialIntMatrix: Array<IntArray> = getDifferentialIntMatrix(greysIntMatrix, width, height)
    val gradientModuleImage = BufferedImage(width - 1, height - 1, BufferedImage.TYPE_INT_ARGB)
    for (i in 0 until width - 1) {
        for (j in 0 until height - 1) {
            //I set the "opposite" grey tonality, to obtain the borders blacker, and the rest whiter
            val grey = Color(
                255 - differentialIntMatrix[i][j],
                255 - differentialIntMatrix[i][j],
                255 - differentialIntMatrix[i][j]
            )
            gradientModuleImage.setRGB(i, j, grey.rgb)
        }
    }
    return gradientModuleImage
}
