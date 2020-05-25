package generator.topography

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.floor
import kotlin.math.sqrt

fun getColorIntMatrix(image: BufferedImage): Array<IntArray> {
    val height = image.height
    val width = image.width
    val colorMatrix = Array(width) { IntArray(height) }
    for (i in 0 until width) {
        for (j in 0 until height) {
            colorMatrix[i][j] = image.getRGB(i, j)
        }
    }
    return colorMatrix
}

fun getGreysIntMatrix(colorIntMatrix: Array<IntArray>, width: Int, height: Int): Array<IntArray> {
    val greysIntMatrix = Array(width) { IntArray(height) }
    for (i in 0 until width) {
        for (j in 0 until height) {
            val c = Color(colorIntMatrix[i][j])
            //average of the three colors
            greysIntMatrix[i][j] = (c.blue + c.red + c.green) / 3
        }
    }
    return greysIntMatrix
}

/**
 * optimized Version, get the matrix directly from the image
 */
fun getGreysIntMatrix(image: BufferedImage): Array<IntArray> {
    val height = image.height
    val width = image.width
    val greysIntMatrix = Array(width) { IntArray(height) }
    var c: Color? = null
    for (i in 0 until width) {
        for (j in 0 until height) {
            c = Color(image.getRGB(i, j))
            greysIntMatrix[i][j] = (c.blue + c.red + c.green) / 3
        }
    }
    return greysIntMatrix
}

fun getDifferentialIntMatrix(intMatrix: Array<IntArray>, width: Int, height: Int): Array<IntArray> {
    val differentialIntMatrix = Array(width - 1) { IntArray(height - 1) }
    for (i in 0 until width - 1) {
        for (j in 0 until height - 1) {
            //two components of the "gradient" vector
            val diffx = intMatrix[i][j + 1] - intMatrix[i][j]
            val diffy = intMatrix[i + 1][j] - intMatrix[i][j]
            // I put the module in the matrix though
            differentialIntMatrix[i][j] = floor(sqrt(diffx * diffx + diffy * diffy.toDouble())).toInt()
        }
    }
    return differentialIntMatrix
}

fun drawBordersBlackAndWhite(
    imageBorders: BufferedImage,
    differentialIntMatrix: Array<IntArray>,
    width: Int,
    height: Int,
    threshold: Int
) {
    for (i in 0 until width - 1) {
        for (j in 0 until height - 1) {
            val isBorder = differentialIntMatrix[i][j] > threshold
            if (isBorder) {
                imageBorders.setRGB(i, j, Color.black.rgb)
            } else {
                imageBorders.setRGB(i, j, Color.white.rgb)
            }
        }
    }
}

fun getColorImage(width: Int, height: Int, color: Color): BufferedImage {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    for (i in 0 until width - 1) {
        for (j in 0 until height - 1) {
            image.setRGB(i, j, color.rgb)
        }
    }
    return image
}

fun getSingleColorIntMatrix(
    colorIntMatrix: Array<IntArray>,
    width: Int,
    height: Int,
    color: Color
): Array<IntArray> {
    val singleColorRGBIntMatrix = Array(width) { IntArray(height) }
    for (i in 0 until width) {
        for (j in 0 until height) {
            val c = Color(colorIntMatrix[i][j])
            when (color) {
                Color.red -> singleColorRGBIntMatrix[i][j] = c.red
                Color.blue -> singleColorRGBIntMatrix[i][j] = c.blue
                Color.green -> singleColorRGBIntMatrix[i][j] = c.green
            }
        }
    }
    return singleColorRGBIntMatrix
}

fun invertBlackAndWhite(image: BufferedImage): BufferedImage {
    val imageOut = getColorImage(image.width, image.height, Color.BLACK)
    for (i in 0 until image.width) {
        for (j in 0 until image.height) {
            //invert white into black
            when (Color(image.getRGB(i, j))) {
                Color.white -> imageOut.setRGB(i, j, Color.black.rgb)
                Color.black -> imageOut.setRGB(i, j, Color.white.rgb)
                else -> imageOut.setRGB(i, j, image.getRGB(i, j))
            }
        }
    }
    return imageOut
}

fun drawBorders(
    imageBorders: BufferedImage,
    differentialIntMatrix: Array<IntArray>,
    width: Int,
    height: Int,
    threshold: Int,
    borderColor: Color
) {
    for (i in 0 until width - 1) {
        for (j in 0 until height - 1) {
            if (differentialIntMatrix[i][j] > threshold) {
                imageBorders.setRGB(i, j, imageBorders.getRGB(i, j) + borderColor.rgb)
            }
        }
    }
}
