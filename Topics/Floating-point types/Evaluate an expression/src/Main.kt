import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    println(List(4) { scanner.nextDouble() }.let { it[0] * 10.5 + it[1] * 4.4 + (it[2] + it[3]) / 2.2 })
}

//fun main() = List(4) { readLine()!!.toDouble() }.let { println(it[0] * 10.5 + it[1] * 4.4 + (it[2] + it[3]) / 2.2) }