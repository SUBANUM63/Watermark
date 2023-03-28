fun main() {

    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()

    // put your code here
    var Max = a
    if (a < b) Max = b
    println(Max)
}
