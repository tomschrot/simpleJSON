package simpleJSON

/**
 * Helper to handle nested arrays in JSON
 *
 * @author Thomas Schröter
 */
class List(): JointString {
    //-------------------------------------------------------------------------
    private val _list = ParsedList()
    //-------------------------------------------------------------------------
    constructor(block: List.(ref: List) -> Unit) : this() { block(this) }
    //-------------------------------------------------------------------------
    infix fun <T> List.add(value: T) =
            _list.add(value)
    //-------------------------------------------------------------------------
    operator fun <T> plusAssign(value: T) {
        _list.add(value)
    }
    //-------------------------------------------------------------------------
    override fun toString(): String {
        return asString('[', ']')
    }
    //-------------------------------------------------------------------------
    override fun iterateItems(sb: StringBuilder) {
        var count = 0
        _list.forEach { item ->
            appendItem(item, sb)
            if(++count < _list.count())
                sb.append(", ")
        }
    }
    //-------------------------------------------------------------------------
}