fun main() {
    val pb = readln().toInt()
    val isWeekend = readln()

    if (isWeekend.lowercase() == "true") {
        println(if (pb in 15..25) "true" else "false")
    } else if (isWeekend.lowercase() == "false") {
        println(if (pb in 10..20) "true" else "false")
    }
}