
fun main(args: Array<String>) {

    println("Usage of simpleJSON package\n")

    println("Use simpleJSON.Create dsl to create an object and")
    val testIn =
            simpleJSON.Create { // using constructor syntax
                "name"    be "simpleJSON lib for Kotlin-JS"
                "version" be 1.0
                "date"    be "2018-05-15"

                // nested objects are possible
                "author" be simpleJSON.Create {
                    "name"       be "Tom"
                    "surname"    be "Schröter"
                    "profession" be "developer"
                }
            }

    // you may add more objects anytime
    testIn += { "note" be "Please note, that the elements in the resulting string are unsorted!"}

    println("output as JSON string:\n")
    println(testIn)

    // now do the reverse
    println("\nThe JSON string is converted into a Kotlin HashMap:\n")
    var testOut = simpleJSON.Parser convert testIn.asString
    println(testOut)

    // easy access to 1st level elements
    println("\nYou can access the elements, e.g.:\n")
    println("${testOut["name"]}")

    // access elements of nested objects via
    simpleJSON.asMap(testOut["author"]) {
        println("by ${this["name"]} ${this["surname"]}")
    }
    println(testOut["note"])

    println("\nNow let's use a literal:")

    val nextTest =
"""
{
    "name"  : "JSON literal as input",
    "empty" : "",
    "evil"  : null,
    "list"  : ["is", "also", "possible"]
}
"""
    println(nextTest)

    println("It parses into map with nested list:\n")
    testOut = simpleJSON.Parser.convert(nextTest)
    println(testOut)

    println("\nAccess to the list elements ")
    simpleJSON.asList(testOut["list"]) {
        forEach {
            item ->
            println(item)
        }
    }
    println("where the list elements are in a row!\n")
    println("\nThat's about it.")
    println("If you have suggestions and/or questions, drop me a note.")
}
