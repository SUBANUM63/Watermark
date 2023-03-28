import java.io.File

class Rectangle(val width: Int, val height: Int)

fun printArea(rectangle: Rectangle) {
    // ...
    println(rectangle.width * rectangle.height)
}

fun printXor(condition1: Int, condition2: Int) {
    println(condition1 xor condition2)
}

fun main() {
//    val condition1 = 175892
//    val condition2 = 98795
//    println(condition1 and condition2)
//    println(condition1 xor condition2)
//    if (condition1 and condition2 > 35925)
//        printXor(condition1 - condition2, condition1 or condition2) // 1
//    else
//        printXor(condition1 xor condition2, condition1 and condition2) // 2
    val line = File("text.txt").readText().split(' ')
    println(line.size)
}