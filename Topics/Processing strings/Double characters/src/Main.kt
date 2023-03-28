fun main() {
    // write your code here    
    val a = readln()
    var b = ""
    for (ch in 0..(a.length - 1)) {
        b = b + a[ch] + a[ch]
    }
    println(b)
}
