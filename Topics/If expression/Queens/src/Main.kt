import kotlin.math.*
fun main() {
    // write your code here
    val (x1, y1) = readLine()!!.split(' ').map(String::toInt)
    val (x2, y2) = readLine()!!.split(' ').map(String::toInt)
   
    println(if (x1 == x2 || y1 == y2 || abs(x1 - x2) == abs(y1 - y2)) {"YES"} else {"NO"})

}
