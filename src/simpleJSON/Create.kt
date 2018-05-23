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
class Create(): Builder {
    //-------------------------------------------------------------------------
    private val _map = ParsedMap()
    //-------------------------------------------------------------------------
    val asString: String
        get() = toString()
    //-------------------------------------------------------------------------
    constructor(block: Context): this() { block() }
    //-------------------------------------------------------------------------
    infix fun <T> String.map(value: T) =
            _map.put(this, value)
    //-------------------------------------------------------------------------
    operator fun plusAssign(block: Context) =
            block()
    //-------------------------------------------------------------------------
    infix fun list(block: List.() -> Unit): List =
        List().apply { block() }
    //-------------------------------------------------------------------------
    override fun iterateItems(sb: StringBuilder) {
        var count = 0
        _map.forEach { item ->
            appendItem(item.value, sb)
            if(++count < _map.size)
                sb.append(", ")
        }
    }

    //-------------------------------------------------------------------------
    override fun toString(): String =
            StringBuilder().apply {
                var count = 0
                append("{")

                _map.forEach{ item ->
                    append("\"")
                    append(item.key)
                    append("\":")

                    when(item.value) {
                        is List,
                        is Create -> append(item.value.toString())
                        else      -> {
                            append("\"")
                            append(item.value.toString())
                            append("\"")
                        }
                    }

                    if(++count < _map.size)
                        append(", ")
                }
                append("}")
            }.toString()
    //-------------------------------------------------------------------------
}