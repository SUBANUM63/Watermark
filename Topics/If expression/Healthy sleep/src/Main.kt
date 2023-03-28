fun main() {    
    // write your code here
    val A = readln().toInt()
    val B = readln().toInt()
    val H = readln().toInt()
    println(if(H < A){"Deficiency"}
           else if (H > B){"Excess"}
           else {"Normal"})
}
