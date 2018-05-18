package simpleJSON

private typealias Context = Create.() -> Unit

/**
 * Very simple but straightforward DSL to create nested JSON strings.
 * It does not cover the complete JSON features! I use it for inter process
 * communication and that works great.
 * See main() for demo.
 *
 * @author Tom Schröter
 *
 */
class Create() {
    //-------------------------------------------------------------------------
    private val _map = ParsedMap()
    //-------------------------------------------------------------------------
    val asString: String
        get() = toString()
    //-------------------------------------------------------------------------
    constructor(block: Context): this() {
        block()
    }
    //-------------------------------------------------------------------------
    infix fun <T> String.be(value: T) =
            _map.put(this, value)
    //-------------------------------------------------------------------------
    operator fun plusAssign(block: Context) =
            block()
    //-------------------------------------------------------------------------
    override fun toString(): String =
            StringBuilder().apply {
                var count = 0
                append("{")
                for (n in _map) {
                    append("\"")
                    append(n.key)
                    append("\":")
                    if(n.value !is Create) {
                        append("\"")
                        append(n.value.toString())
                        append("\"")
                    }
                    else append(n.value.toString())
                    if(++count < _map.size)
                        append(", ")
                }
                append("}")
            }.toString()
    //-------------------------------------------------------------------------
}