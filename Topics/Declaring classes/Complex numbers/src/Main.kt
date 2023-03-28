import java.io.File                   // Import the File class for file handling
import javax.imageio.ImageIO          // Import the ImageIO class for reading and writing images
import java.awt.image.BufferedImage   // BufferedImage Class
import java.awt.Color                 // Color class

/**
 * A Color instance can be initiated in many ways:

Color(rgb), where rgb is the integer value of 24-bit color read by the getRGB() function (without alpha channel);

Color(argb, true), where argb is the integer value of 32-bit color (with alpha channel);

Color(r, g, b), where r, g, and b are the values for each basic color;

Color(r, g, b, a) where r, g, and b are the values for each basic color, and a is the value of the alpha channel.
 */
fun main() {
    val inputFile = File("24bit.png")  // Create a file instance in order to read the "24bit.png" image file
    val inputZeinu = File("profile.jpg")
    // Create a BufferedImage instance from the 24-bit image file data
    val myImage: BufferedImage = ImageIO.read(inputFile)
    val zeinuImage: BufferedImage = ImageIO.read(inputZeinu)
    // myImage.width is the image width
    // myImage.height is the image height
    for (x in 0 until myImage.width) {               // For every column.
        for (y in 0 until myImage.height) {          // For every row
            val color = Color(myImage.getRGB(x, y))  // Read color from the (x, y) position

            val g = color.green              // Access the Green color value
            val b = color.blue               // Access the Blue color value
            // Use color.red in case the Red color is needed

            val colorNew = Color(255, g, b)  // Create a new Color instance with the red value equal to 255
            myImage.setRGB(x, y, colorNew.rgb)  // Set the new color at the (x, y) position
        }
    }
    val outputFileJpg = File("newImageFile.jpg")  // Output the file
    ImageIO.write(myImage, "jpg", outputFileJpg)  // Create an image using the BufferedImage instance data

    for (x in 0 until zeinuImage.width) {               // For every column.
        for (y in 0 until zeinuImage.height) {          // For every row
            val color = Color(zeinuImage.getRGB(x, y))  // Read color from the (x, y) position
            val r = color.red
            val g = color.green              // Access the Green color value
            val b = color.blue               // Access the Blue color value
            // Use color.red in case the Red color is needed

            val colorNew = Color(b, g, r)  // Create a new Color instance with the red value equal to 255
            zeinuImage.setRGB(x, y, colorNew.rgb)  // Set the new color at the (x, y) position
        }
    }
    val outputFileZeinu = File("newZeinuImageFile.jpg")  // Output the file
    ImageIO.write(zeinuImage, "jpg", outputFileZeinu)  // Create an image using the BufferedImage instance data

    alpha32BitColor()
}

/**
 * In the following code example, a 32-bit image file with alpha channel is created.
 * Alpha channel varies from 0 at the top of the image (total transparent) to 255 at the bottom (total opaque).
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//useful code
//Get alpha value from Color instance
//val c = Color(255, 0, 0, 127)  // Create a color instance, with alpha equal to 127
//val alpha = c.alpha            // Get alpha channel value
 ////////////////////////////////////////////////////////////////////////////////////////
// Create Color instance for pixel at (x, y) position, alpha channel is also set
val color = Color(bI.getRGB(x, y), true)  // where bI is a BufferedImage instance

The above code creates a new Color instance from the color of bI – the BufferedImageinstance at the position (x, y). If we didn't use this particular initiation with the second parameter set to true, then the alpha channel value at the position (x, y) would be ignored and set to value 255. This happens in the following code snippet. No matter what the alpha value was, it is disregarded and set to 255.

// Create Color instance for pixel at (x, y) position, only RGB color (alpha set to 255)
val color = Color(bI.getRGB(x, y))  // where bI is a BufferedImage instance
*/
fun alpha32BitColor() {
    // Create a new BufferedImage instance with image size 256 X 256
    // The first parameter is the image width, while the second is the image height
    // The third parameter should be BufferedImage.TYPE_INT_ARGB for a 32-bit image
    // or BufferedImage.TYPE_INT_RGB for a 24-bit image
    val myImage: BufferedImage = BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB)

    for (i in 0 until myImage.width) {
        for (j in 0 until myImage.height) {
            myImage.setRGB(i, j, Color(0, 255, 0, j).rgb)  // Green color with alpha channel value equal to j
        }
    }
    val outputFile = File("alpha.png")         // Output the image
    ImageIO.write(myImage, "png", outputFile)  // Create an image using the BufferedImage
}


// write the class Complex here
class Complex(var real: Double = 0.0, var image: Double = 0.0)

