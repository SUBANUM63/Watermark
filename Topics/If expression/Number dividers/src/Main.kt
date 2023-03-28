fun main() {
    // put your code here
    val number = readLine()!!.toInt()
    if (number % 2 == 0) println("Divided by 2")
    if (number % 3 == 0) println("Divided by 3")
    if (number % 5 == 0) println("Divided by 5")
    if (number % 6 == 0) println("Divided by 6")
}