import java.util.Scanner

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val a = scanner.nextBigInteger()
    val b = scanner.nextBigInteger()
    println(((a + b) + (a - b).abs()) / 2.toBigInteger())

}