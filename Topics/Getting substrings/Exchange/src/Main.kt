fun main() {
    // put your code here
    val a = readln()
    val b = a[a.length - 1] + a.substring(1, (a.length - 1)) + a[0]
    println(b)
}
