
fun main() {
    // write your code here
    val a = readln()
    var b = 0
    var j = a.length - 1
    for (i in 0 until a.length) {
        if (a[i] == a[j]) {
            b++
        }
        j--
    }
    println(if (b == a.length) "yes" else "no")
}
