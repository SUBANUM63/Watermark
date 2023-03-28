// You can experiment here, it won’t be checked

fun main(args: Array<String>) {

    println(6 xor 9)
    /**
     * Creating objects of generic classes
    When we create an instance of a generic class,
    we need to provide a concrete type as a type argument:
     */
    //But if the type is a standard one like Int, String, Double, etc.,
    //you can omit the type argument, as the compiler infers it:
    val obj1: Box<Int> = Box<Int>(123)
    val obj2: Box<String> = Box<String>("abc")

    /**
     * After we have created an object of the generic class
     * and specified the type argument, we can invoke methods of the class:
     */
    println(obj1.showObjectInBox()) // 123
    println(obj2.showObjectInBox()) // "abc"

    //If the class has more than one type parameter, we should specify all of them:
    val obj = Three<String, Int, Int>("abc", 1, 2)

    //The following code creates an instance of RandomCollection for storing four elements in it.

    var nums = RandomCollection(listOf(1, 2, 3, 4))
    for (i in 0 until nums.length()) {
        print("${nums.get(i)} ") // "1 2 3 4 "
    }
//as you can see, You can parametrize RandomCollection with any Basic types, standard classes,
// and your own classes.
    val nameLastname: Pair<String, String> = Pair("Johh", "Smith")
    val nameAge: Pair<String, Int> = Pair("Kite", 18)

    nameLastname.changeFirst("John")
    nameLastname.changeSecond("Smith")

    nameAge.changeFirst("Kate")
    nameAge.changeSecond(19)

    nameLastname.printData()
    nameAge.printData()
}
///Generic classes
/**
 * After the class has been declared, the type parameter can be used as an ordinary type inside the class body. In the example above, the type parameter T is used as:

a type for a field;

a constructor argument type;

an instance method argument and a return type
 */
class Box<T>(t: T) {
    fun showObjectInBox(): T? {
        return value
    }

    /* Constructor accepts
    * a variable of "some type"
    * and sets it to a field */

    var value = t  // A field of "some type"
        get() = field
        set(value) {
            field = value;
        }

}

//Generic and several type parameters
class Three<T, U, V>(var first: T, var second: U, var third: V)

//example
class Pair<T, P>(var first: T, var second: P) {
    fun changeFirst(newValue: T) {
        first = newValue
    }

    fun changeSecond(newValue: P) {
        second = newValue
    }

    fun printData() {
        println("Values:")
        println("first = $first")
        println("second = $second")
    }
}
/**
 * Naming convention for type parameters
The type parameter is named with a single letter according to the convention in order to distinguish it from an ordinary class name.

The most commonly used type parameter names are:

T – Type;

S, U, V, etc. – 2nd, 3rd, 4th types;

E – Element (often used by different collections);

K – Key;

V – Value;

N – Number.
 */

/**
 * Creating your own collection
Let's create a generic class called RandomCollection.
It stores a list of items of some type, a constructor to set the items,
and a method to get an element of the list by its index.
The collection we are creating is immutable.
 */

class RandomCollection<T>(val items: List<T>) {
    fun get(index: Int): T {
        return items[index]
    }

    fun length(): Int {
        return items.size
    }
}