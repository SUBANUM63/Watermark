fun main() {
    var max = ""
    val st = readln()
    val list = mutableListOf<String>()
     list.addAll(st.split(' '))

    for (i in list.indices) {
        if (max.length < list[i].length) max = list[i]
    }
    println(max)


    /* prints 7 */
    println(Player.Properties.defaultSpeed)


    /* prints 13 */
    println(Player.Factory.create(13).id)
}

////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
//fun main() = readLine()!!.split(" ").maxByOrNull { it.length }.let(::print)

//eeeeeeeeexxxxxxxxxxxxxxxxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
/**
 * In this example, we've created an additional singleton that can create a new instance of the class.
 * This pattern is called Factory and can be really useful for complex cases.
 *
 * The usual way to use this pattern is through nested objects.
 */
class Player(val id: Int) {
    object Properties {
        /* Default player speed in playing field – 7 cells per turn */
        val defaultSpeed = 7

//        fun calcMovePenalty(cell: Int): Int {
//            /* calc move speed penalty */
//        }
    }

    /* creates a new instance of Player */
    object Factory {
        fun create(playerId: Int): Player {
            return Player(playerId)
        }
    }
}

//
///* prints 7 */
//println(Player.Properties.defaultSpeed)
//
//
///* prints 13 */
//println(Player.Factory.create(13).id)

/**
 *
 */
