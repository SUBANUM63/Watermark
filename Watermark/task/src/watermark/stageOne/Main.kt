package watermark.stageOne

import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun stageOne() {
    val input = Scanner(System.`in`)
    println("Input the image filename:")
    val fileName = input.next()
    val inputFile = File(fileName)  // Create a file instance in order to read the "24bit.png" image file
    if (inputFile.exists()) {
        val myImage: BufferedImage = ImageIO.read(inputFile)
        printPropertiesAcquired(myImage, fileName)
    }else println("The file $fileName doesn't exist.")

}

fun printPropertiesAcquired(image: BufferedImage, name: String) {
    val transparency = when(image.transparency){
        1 -> "OPAQUE"
        2 -> "BITMASK"
        else-> "TRANSLUCENT"
    }
    println("Image file: $name")
    println("Width: ${image.width}")
    println("Height: ${image.height}")
    println("Number of components: ${image.colorModel.numComponents}")
    println("Number of color components: ${image.colorModel.numColorComponents}")
    println("Bits per pixel: ${image.colorModel.pixelSize}")
    println("Transparency: $transparency")
}