package watermark.stageFive

import java.awt.Color
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import kotlin.system.exitProcess


const val IMAGE = "image"
const val WATER_MARK = "watermark"

enum class PositionType {
    SINGLE, GRID
}

fun stageFive() {

    val scanner = Scanner(System.`in`)
    println("Input the image filename:")
    val inputImage = readImage(scanner.next(), IMAGE)

    println("Input the watermark image filename:")
    val watermarkImage = readImage(scanner.next(), WATER_MARK)

    checkValidityOfDimension(inputImage, watermarkImage)
    val userChoice = validatedWatermarkTransparency(watermarkImage)

    val chosenTransparencyColor = getTransparencyColor(watermarkImage, userChoice)

    println("Input the watermark transparency percentage (Integer 0-100):")
    val weight = validatedWatermarkWeight(scanner.next())

    val maxDiffX = inputImage.width - watermarkImage.width
    val maxDiffY = inputImage.height - watermarkImage.height
    val position = getValidPosition(maxDiffX, maxDiffY)

    println("Input the output image filename (jpg or png extension):")
    val outputFilename = validatedOutputFilename(scanner.next())


    val blendedImage = blendImages(inputImage, watermarkImage, weight, position, chosenTransparencyColor)
    ImageIO.write(blendedImage, outputFilename.split(".").last(), File(outputFilename))

    println("The watermarked image $outputFilename has been created.")


}

fun readImage(fileName: String, imageType: String): BufferedImage {

    val image = File(fileName)
    if (!image.exists()) {
        println("The file $fileName doesn't exist.").also { exitProcess(11) }
    }

    val bufferedImage = ImageIO.read(image)
    if (bufferedImage.colorModel.numColorComponents != 3) {
        println("The number of $imageType color components isn't 3.").also { exitProcess(22) }
    } else if (bufferedImage.colorModel.pixelSize !in listOf(24, 32)) {
        println("The $imageType isn't 24 or 32-bit.").also { exitProcess(33) }
    }
    return bufferedImage
}

fun isInteger(input: String) = input.matches("\\d+".toRegex())
fun validatedWatermarkWeight(weight: String): Int {
    if (!isInteger(weight)) {
        println("The transparency percentage isn't an integer number.").also { exitProcess(44) }
    }

    if (weight.toInt() !in 0..100) {
        println("The transparency percentage is out of range.").also { exitProcess(55) }
    }
    return weight.toInt()
}

fun checkValidityOfDimension(image: BufferedImage, watermark: BufferedImage) {
    if (image.width < watermark.width ||
        image.height < watermark.height
    ) {
        println("The watermark's dimensions are larger.").also { exitProcess(66) }
    }
}

fun validatedOutputFilename(name: String): String {

    if (name.endsWith(".jpg") || name.endsWith(".png")) {
        return name
    } else {
        println("The output file extension isn't \"jpg\" or \"png\".").also { exitProcess(77) }
    }

}

fun blendImages(
    image: BufferedImage, watermark: BufferedImage, weight: Int,
    position: Position,
    transparencyColor: TransparencyColor
): BufferedImage {
    val blendedImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)

    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val i = Color(image.getRGB(x, y))

            val w = if (position.type == PositionType.GRID) {
                Color(watermark.getRGB(x % watermark.width, y % watermark.height), transparencyColor.use)
            } else {
                if (x in position.pair!!.first until (position.pair.first + watermark.width) &&
                    y in position.pair!!.second until (position.pair.second + watermark.height)
                ) {
                    Color(watermark.getRGB(x - position.pair.first, y - position.pair.second), transparencyColor.use)
                } else
                    Color(image.getRGB(x, y))
            }

            val watermarkColorIsTransparencyColor =
                w.red == transparencyColor.color?.red &&
                        w.green == transparencyColor.color.green &&
                        w.blue == transparencyColor.color.blue

            val color = if (w.alpha == 0 || watermarkColorIsTransparencyColor) {
                Color(i.red, i.green, i.blue)
            } else {
                Color(
                    (weight * w.red + (100 - weight) * i.red) / 100,
                    (weight * w.green + (100 - weight) * i.green) / 100,
                    (weight * w.blue + (100 - weight) * i.blue) / 100
                )
            }

            blendedImage.setRGB(x, y, color.rgb)
        }
    }

    return blendedImage
}

fun validatedWatermarkTransparency(watermark: BufferedImage): Boolean {
    if (watermark.colorModel.transparency == Transparency.TRANSLUCENT) {
        println("Do you want to use the watermark's Alpha channel?")
        if (readln().uppercase() == "YES") {
            return true
        }
    }
    return false
}

fun getTransparencyColor(watermark: BufferedImage, useWatermarkTransparency: Boolean): TransparencyColor {
    // Watermark uses alpha channel -> do nothing
    if (watermark.colorModel.pixelSize == 32) {
        return TransparencyColor(useWatermarkTransparency)
    }

    println("Do you want to set a transparency color?")
    if (readln().uppercase() != "YES") {
        return TransparencyColor(false)
    }

    println("Input a transparency color ([Red] [Green] [Blue]):")
    val inputColor = readLine()!!
    if ("(\\d+) (\\d+) (\\d+)".toRegex().matches(inputColor)) {
        val (r, g, b) = "(\\d+) (\\d+) (\\d+)".toRegex().matchEntire(inputColor)!!.destructured
        try {
            return TransparencyColor(useWatermarkTransparency, Color(r.toInt(), g.toInt(), b.toInt()))
        } catch (ex: IllegalArgumentException) { // if color values are not in 0..255
            println("The transparency color input is invalid.").also { exitProcess(88) }
        }
    } else {
        println("The transparency color input is invalid.").also { exitProcess(99) }
    }
}


fun getValidPosition(maxDiffX: Int, maxDiffY: Int): Position {
    println("Choose the position method (single, grid):")
    return when (readLine()!!.lowercase()) {
        "single" -> Position(PositionType.SINGLE, getValidSinglePosition(maxDiffX, maxDiffY))
        "grid" -> Position(PositionType.GRID)
        else -> {
            println("The position method input is invalid.")
            exitProcess(88)
        }

    }
}

fun getValidSinglePosition(maxDiffX: Int, maxDiffY: Int): Pair<Int, Int> {
    println("Input the watermark position ([x 0-$maxDiffX] [y 0-$maxDiffY]):")
    val input = readln()

    val twoIntegers = "(-?\\d+) (-?\\d+)".toRegex()
    if (!input.matches(twoIntegers)) {
        println("The position input is invalid.").also { exitProcess(77) }
    }

    val (x, y) = twoIntegers.matchEntire(input)!!.destructured

    if (x.toInt() !in 0..maxDiffX ||
        y.toInt() !in 0..maxDiffY
    ) {
        println("The position input is out of range.").also { exitProcess(66) }
    }

    return Pair(x.toInt(), y.toInt())
}

class Position(val type: PositionType, val pair: Pair<Int, Int>? = null)

class TransparencyColor(val use: Boolean, val color: Color? = null)