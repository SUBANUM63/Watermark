fun main() {
    // write your code here
    val num = readln()
    val arr = mutableListOf<Int>()
    var leftSum = 0
    var rightSum = 0
    for (i in num)
        arr.add(i.code)
    for (i in 0 until arr.size / 2)
        leftSum += arr[i]
    for (i in arr.size/2  until arr.size)
        rightSum += arr[i]
    println(if (leftSum == rightSum) "YES" else "NO")
}