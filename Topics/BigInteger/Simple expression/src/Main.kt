import java.math.BigInteger

fun main() {
    val list = mutableListOf<BigInteger>()
    for (i in 0..3) {
        list.add(readln().toBigInteger())
    }
    println(-list[0] * list[1] + list[2] - list[3])
}