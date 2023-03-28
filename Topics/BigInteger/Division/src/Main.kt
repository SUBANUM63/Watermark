import java.util.Scanner

fun main() {
    // write your code here
    val scanner = Scanner(System.`in`)
    val bigInt = scanner.nextBigInteger()
    val bigDivisor = scanner.nextBigInteger()
    val (result, remainder) = bigInt.divideAndRemainder(bigDivisor)
    println("$bigInt = $bigDivisor * $result + $remainder")

}