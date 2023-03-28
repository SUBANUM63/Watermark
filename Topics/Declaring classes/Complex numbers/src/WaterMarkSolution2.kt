
import java.awt.Color
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////SOLUTION ONE OF STAGE FOUR ///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
fun getImage(watermark: Boolean = false): BufferedImage {
    println("Input the${if (watermark) " watermark" else ""} image filename:")
    val pathname = readln()
    val file = File(pathname)
    if (!file.exists()) {
        println("The file $pathname doesn't exist.")
        exitProcess(1)
    }

    val image = ImageIO.read(file)
    if (!watermark && image.colorModel.numComponents != 3) {
        println("The number of image color components isn't 3.")
        exitProcess(1)
    } else if (watermark && image.colorModel.numComponents < 3) {
        println("The number of watermark color components isn't 3.")
        exitProcess(1)
    } else if (image.colorModel.pixelSize != 24 && image.colorModel.pixelSize != 32) {
        println("The ${if (watermark) "watermark" else "image"} isn't 24 or 32-bit.")
        exitProcess(1)
    }


    return image
}

fun hasTransparency(image: BufferedImage): Boolean = image.transparency == Transparency.TRANSLUCENT

fun checkIfUseWatermarkTransparency(): Boolean {
    println("Do you want to use the watermark's Alpha channel?")
    val answer = readln()

    return answer.lowercase() == "yes"
}

fun askForTransparencyColor(): Color? {
    println("Do you want to set a transparency color?")
    val useTransparencyColor = readln().lowercase() == "yes"

    if (!useTransparencyColor) {
        return null
    }
    try {
        println("Input a transparency color ([Red] [Green] [Blue]):")
        val colors = readln().split(" ").map { it.toInt() }
        assert(colors.size == 3)

        return Color(colors[0], colors[1], colors[2])
    } catch (e: Throwable) {
        println("The transparency color input is invalid.")
        exitProcess(1)
    }
}

fun areImagesEqualSize(image1: BufferedImage, image2: BufferedImage): Boolean =
    image1.height == image2.height && image1.width == image2.width

fun getTransparencyPercentage(): Int {
    println("Input the watermark transparency percentage (Integer 0-100):")
    val percentage = try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println("The transparency percentage isn't an integer number.")
        exitProcess(1)
    }

    if (!(0..100).contains(percentage)) {
        println("The transparency percentage is out of range.")
        exitProcess(1)
    }

    return percentage
}

fun getOutputFileName(): String {
    println("Input the output image filename (jpg or png extension):")

    val filename = readln()
    if (!filename.endsWith("jpg") && !filename.endsWith("png")) {
        println("The output file extension isn't \"jpg\" or \"png\".")
        exitProcess(1)
    }

    return filename
}

fun getBlendImage(
    image: BufferedImage,
    watermark: BufferedImage,
    percentage: Int,
    useWaterMarkTransparency: Boolean,
    transparencyColor: Color?
): BufferedImage {
    val blendImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)

    for (w in 0 until blendImage.width) {
        for (h in 0 until blendImage.height) {
            val imageColor = Color(image.getRGB(w, h))
            val watermarkColor = Color(watermark.getRGB(w, h), true)

            if (useWaterMarkTransparency && watermarkColor.alpha == 0) {
                blendImage.setRGB(w, h, imageColor.rgb)
                continue
            } else if (transparencyColor?.rgb == watermarkColor.rgb) {
                blendImage.setRGB(w, h, imageColor.rgb)
                continue
            }

            val newColor = Color(
                (percentage * watermarkColor.red + (100 - percentage) * imageColor.red) / 100,
                (percentage * watermarkColor.green + (100 - percentage) * imageColor.green) / 100,
                (percentage * watermarkColor.blue + (100 - percentage) * imageColor.blue) / 100
            )
            blendImage.setRGB(w, h, newColor.rgb)
        }
    }

    return blendImage
}

fun main() {
    val image = getImage()
    val watermarkImage = getImage(true)

    if (!areImagesEqualSize(image, watermarkImage)) {
        println("The image and watermark dimensions are different.")
        exitProcess(1)
    }

    var useWaterMarkTransparency = false
    var transparencyColor: Color? = null
    if (hasTransparency(watermarkImage)) {
        useWaterMarkTransparency = checkIfUseWatermarkTransparency()
    } else {
        transparencyColor = askForTransparencyColor()
    }

    val percentage = getTransparencyPercentage()
    val outputFileName = getOutputFileName()
    val blendImage = getBlendImage(image, watermarkImage, percentage, useWaterMarkTransparency, transparencyColor)

    ImageIO.write(blendImage, outputFileName.takeLast(3), File(outputFileName))
    println("The watermarked image $outputFileName has been created.")
}

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////SOLUTION TWO OF STAGE FOUR ///////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////


fun fileCreator(fileName: String): File {
    val separator = File.separator
    val workingDirectory = System.getProperty ("user.dir")
    val filePath = "${workingDirectory}${separator}" +
            if ("/" in fileName) fileName.replace("/", separator) else fileName
    return File(filePath)
}

class InputImage(type: String) {
    private val fileName: String
    private val imageFile: File
    val image: BufferedImage
    init {
        println("Input the ${if (type == "watermark") "watermark image" else type} filename:")
        fileName = readln()
        imageFile = fileCreator(fileName)
        if (imageFile.exists()) {
            image = ImageIO.read(imageFile)
            if (image.colorModel.numColorComponents != 3) {
                print("The number of $type color components isn't 3.")
                exitProcess(0)
            }
            if (image.colorModel.pixelSize != 24 && image.colorModel.pixelSize != 32) {
                print("The $type isn't 24 or 32-bit.")
                exitProcess(0)
            }
        } else {
            print("The file $fileName doesn't exist.")
            exitProcess(0)
        }
    }
}

object OutputImage {
    private val outputFileName: String
    private val outputFileExtension: String
    init {
        println("Input the output image filename (jpg or png extension):")
        outputFileName = readln()
        outputFileExtension = outputFileName.substringAfter(".")
        if (outputFileExtension != "jpg" && outputFileExtension != "png") {
            print("The output file extension isn't \"jpg\" or \"png\".")
            exitProcess(0)
        }
    }

    fun createOutput(image: BufferedImage, watermark: BufferedImage) {
        val outputImage = Blender.blendImage(image, watermark)
        val outputFile = fileCreator(outputFileName)
        ImageIO.write(outputImage, outputFileExtension, outputFile)
        print("The watermarked image $outputFileName has been created.")
    }
}

object Blender {
    var useAlpha = false
    var useTransparencyColor = false
    private var transparencyColor = Color(0,0,0)
    private var transparencyPercentage = 0

    fun setTransparencyColor() {
        println("Input a transparency color ([Red] [Green] [Blue]):")
        val input = readln()
        if (Regex("\\d{1,3} \\d{1,3} \\d{1,3}").matches(input)) {
            val (red, green, blue) = input.split(" ").map { it.toInt() }
            if (red !in 0..255 || green !in 0..255 || blue !in 0..255) {
                print("The transparency color input is invalid.")
                exitProcess(0)
            } else {
                transparencyColor = Color(red, green, blue)
            }
        } else {
            print("The transparency color input is invalid.")
            exitProcess(0)
        }
    }

    fun setTransparencyPercentage() {
        println("Input the watermark transparency percentage (Integer 0-100):")
        val input = readln()
        if (Regex("\\d{1,3}").matches(input)) {
            transparencyPercentage = input.toInt()
            if (transparencyPercentage !in 0..100) {
                print("The transparency percentage is out of range.")
                exitProcess(0)
            }
        } else {
            print("The transparency percentage isn't an integer number.")
            exitProcess(0)
        }
    }

    fun blendImage(image: BufferedImage, watermark: BufferedImage): BufferedImage {
        val outputImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)

        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val i = Color(image.getRGB(x, y))
                val w = Color(watermark.getRGB(x, y), true)
                if (useAlpha && w.alpha == 0) {
                    val color = Color(i.red, i.green, i.blue)
                    outputImage.setRGB(x, y, color.rgb)
                } else if (useTransparencyColor &&
                    w.red == transparencyColor.red &&
                    w.green == transparencyColor.green &&
                    w.blue == transparencyColor.blue) {
                    val color = Color(i.red, i.green, i.blue)
                    outputImage.setRGB(x, y, color.rgb)
                } else {
                    val color = Color(
                        (transparencyPercentage * w.red + (100 - transparencyPercentage) * i.red) / 100,
                        (transparencyPercentage * w.green + (100 - transparencyPercentage) * i.green) / 100,
                        (transparencyPercentage * w.blue + (100 - transparencyPercentage) * i.blue) / 100)
                    outputImage.setRGB(x, y, color.rgb)
                }
            }
        }

        return outputImage
    }
}

fun main() {
    val input = InputImage("image")
    val watermark = InputImage("watermark")

    if (!(input.image.width == watermark.image.width && input.image.height == watermark.image.height)) {
        print("The image and watermark dimensions are different.")
        exitProcess(0)
    }

    if (watermark.image.transparency == 3) {
        println("Do you want to use the watermark's Alpha channel?")
        Blender.useAlpha = readln().lowercase() == "yes"
    } else {
        println("Do you want to set a transparency color?")
        Blender.useTransparencyColor = readln().lowercase() == "yes"
        if (Blender.useTransparencyColor) Blender.setTransparencyColor()
    }

    Blender.setTransparencyPercentage()

    val output = OutputImage
    output.createOutput(input.image, watermark.image)
}