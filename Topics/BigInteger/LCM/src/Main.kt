import java.util.Scanner

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val number1 = scanner.nextBigInteger()
    val number2 = scanner.nextBigInteger()
    println((number1 * number2) / (number1.gcd(number2)))
}