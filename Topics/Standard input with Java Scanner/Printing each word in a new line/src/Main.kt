import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val list = List(5) { scanner.next() }
    list.forEach { println(it) }

}