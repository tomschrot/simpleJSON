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
class Create(): JointString {
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
    override fun toString(): String {
        return asString('{', '}')
    }
    //-------------------------------------------------------------------------
    override fun iterateItems(sb: StringBuilder) {
        var count = 0
        _map.forEach { item ->
            sb.append("\"")
            sb.append(item.key)
            sb.append("\":")

            appendItem(item.value, sb)

            if(++count < _map.size)
                sb.append(", ")
        }
    }
    //-------------------------------------------------------------------------
}