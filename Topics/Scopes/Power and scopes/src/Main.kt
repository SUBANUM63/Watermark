//fun main() {
//    // change the code below
//    var thirteen = 13L
//    for (i in 2..10) {
//        thirteen *= 13
//        println(thirteen)
//    }
//}
///////////////////////////////////////////////////////////////////////////////////////////////////////
/// comparing inner class and nested class
///////////////////////////////////////////////////////////////////////////////////////////////////////
class Superhero {
    val power = 1000

    class MagicCloak {
        // you cannot access something from Superhero here
        /**
         * A regular nested class cannot access members of its outer class.
         * But a nested class marked as an inner class can. without creating outer classes
         * object like below
         */
        val magicPower = 100*Superhero().power
    }
    // you need to create a MagicCloak object to access its members
    val magicPower = power * MagicCloak().magicPower

    class Hammer {
        // you cannot access power property from Superhero here
        val mightPower = 100
    }
    val mightPower = power * Hammer().mightPower
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////// inner class//// use this@Cat.color to use outer class variable having the same ......
///////////////////////////////////////////////////////////////////////////////////////////////////////////
class Cat(val name: String, val color: String, val size: Int) {

    fun sayMeow() {
        println("$name says: \"Meow\".")
    }

    inner class Bow(val color: String, val size: Int) {
        fun printColor() {
            println("The cat named $name is $color. ")
            println("The cat named $name has a ${this.color} bow. ") // you can discard this keyword

            println("The size of $name is $size. ")
            println("The size of the bow is ${this.size}.")

            println("The cat named $name has a ${this@Cat.color} bow. ")
            println("The size of the bow is ${this@Cat.size}.")

        }
    }
}

fun main() {
    val cat = Cat("Bob", "white", 10)
    val bow = cat.Bow("red", 15)
    bow.printColor()
}