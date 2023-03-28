package watermark.stageThree

import java.awt.Color
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import kotlin.system.exitProcess


const val IMAGE = "image"
const val WATER_MARK = "watermark"
fun stageThree() {

    val scanner = Scanner(System.`in`)
    println("Input the image filename:")
    val inputImage = readImage(scanner.next(), IMAGE)
    println("Input the watermark image filename:")
    val watermarkImage = readImage(scanner.next(), WATER_MARK)
    val userChoice = validatedWatermarkTransparency(watermarkImage)
    compareDimensionsEquality(inputImage, watermarkImage)
    println("Input the watermark transparency percentage (Integer 0-100):")
    val weight = validatedWatermarkWeight(scanner.next())

    println("Input the output image filename (jpg or png extension):")
    val outputFilename = validatedOutputFilename(scanner.next())

    val blendedImage = blendImages(inputImage, watermarkImage, weight, userChoice)
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

fun compareDimensionsEquality(image: BufferedImage, watermark: BufferedImage) {
    if (image.width != watermark.width ||
        image.height != watermark.height
    ) {
        println("The image and watermark dimensions are different.").also { exitProcess(66) }
    }
}

fun validatedOutputFilename(name: String): String {

    if (name.endsWith(".jpg") || name.endsWith(".png")) {
        return name
    } else {
        println("The output file extension isn't \"jpg\" or \"png\".").also { exitProcess(77) }
    }

}

fun blendImages(image: BufferedImage, watermark: BufferedImage, weight: Int, hasAlpha: Boolean): BufferedImage {
    val blendedImage = BufferedImage(watermark.width, watermark.height, BufferedImage.TYPE_INT_RGB)

    for (x in 0 until image.width) {
        for (y in 0 until image.height) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x, y), hasAlpha)
            val color = if (w.alpha == 0) {
                Color(i.red, i.green, i.blue)
            } else
                Color(
                    (weight * w.red + (100 - weight) * i.red) / 100,
                    (weight * w.green + (100 - weight) * i.green) / 100,
                    (weight * w.blue + (100 - weight) * i.blue) / 100
                )
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
