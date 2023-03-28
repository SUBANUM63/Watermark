
fun main() {
    val a = readln()
    var b = ""
    if (a.length % 2 == 1) {
        for (i in a.indices) {
            if (i != a.length / 2) {
                b += a[i]
            }
        }
    } else {
        for (i in a.indices) {
            if (i != a.length / 2 && i != a.length / 2 - 1) {
                b += a[i]
            }
        }
    }
    println(b)
}
