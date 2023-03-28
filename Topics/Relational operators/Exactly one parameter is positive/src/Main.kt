fun main() {
    // put your code here
    val n1 = readln().toInt()
    val n2 = readln().toInt()
    val n3 = readln().toInt()
    println((n1 > 0 && n2 <= 0 && n3 <= 0) || (n1 <= 0 && n2 > 0 && n3 <= 0) || (n1 <= 0 && n2 <= 0 && n3 > 0))

}
