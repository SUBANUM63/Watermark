import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


fun main(){
    drawPolygon()
}
fun drawPolygon(): BufferedImage {
    val height: Int = 300
    val width: Int = 300
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.YELLOW
    val imageFile = File("myFirstImage.png")
    graphics.drawPolygon(intArrayOf(50,100,200,250,200,100), intArrayOf(150,250,250,150,50,50), 6)

    saveImage(image, imageFile)
    return image
}

fun saveImage(image: BufferedImage, imageFile: File) {
    ImageIO.write(image, "png", imageFile)
}