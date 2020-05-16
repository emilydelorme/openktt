package noise

import opensimplex2.OpenSimplex2S
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.WindowConstants


private const val WIDTH = 1024
private const val HEIGHT = 1024
private const val PERIOD = 64.0
private const val OFF_X = 2048
private const val OFF_Y = 2048
private val generateType = GenerateType.AreaGenerator

private const val FREQ = 1.0 / PERIOD

private enum class GenerateType {
    Evaluator, AreaGenerator, ShowDifference
}

fun main(args: Array<String>) {

    // Initialize
    val noise = OpenSimplex2S(1234)
    val context: OpenSimplex2S.GenerateContext3D =
        OpenSimplex2S.GenerateContext3D(OpenSimplex2S.LatticeOrientation3D.XYBeforeZ, FREQ, FREQ, FREQ, 1.0)

    // Generate
    val buffer =
        Array(HEIGHT) { DoubleArray(WIDTH) }
    if (generateType != GenerateType.Evaluator) noise.generate3(
        context,
        arrayOf(buffer),
        OFF_X,
        OFF_Y,
        0
    )

    // Image
    val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until HEIGHT) {
        for (x in 0 until WIDTH) {
            var value = buffer[y][x]
            var evalValue = 0.0
            if (generateType != GenerateType.AreaGenerator) evalValue =
                noise.noise3_XYBeforeZ((x + OFF_X) * FREQ, (y + OFF_Y) * FREQ, 0.0)
            when (generateType) {
                GenerateType.Evaluator -> value = evalValue
                GenerateType.ShowDifference -> value -= evalValue
            }
            if (value < -1) value = -1.0 else if (value > 1) value = 1.0
            val rgb = 0x010101 * ((value + 1) * 127.5).toInt()
            image.setRGB(x, y, rgb)
        }
    }

    // Save it or show it
    if (args.size > 0 && args[0] != null) {
        ImageIO.write(image, "png", File(args[0]))
        println("Saved image as " + args[0])
    } else {
        val frame = JFrame()
        val imageLabel = JLabel()
        imageLabel.icon = ImageIcon(image)
        frame.add(imageLabel)
        frame.pack()
        frame.isResizable = false
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.isVisible = true
    }
}