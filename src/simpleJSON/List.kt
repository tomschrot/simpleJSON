package simpleJSON

internal interface Builder {

    fun asString(open: Char, close: Char): String =
        StringBuilder().apply {
            append(open)
            iterateItems(this)
            append(close)
        }.toString()

    fun iterateItems(sb: StringBuilder)

    fun appendItem(item: Any?, sb: StringBuilder) {
        when(item) {
            is List,
            is Create -> sb.append(item.toString())
            else -> {
                sb.append("\"")
                sb.append(item.toString())
                sb.append("\"")
            }
        }
    }
}

class List(): Builder {
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

    override fun toString(): String {
        return asString('[', ']')
    }

    override fun iterateItems(sb: StringBuilder) {
        var count = 0
        _list.forEach { item ->
            appendItem(item, sb)
            if(++count < _list.count())
                sb.append(", ")
        }
    }

     fun toStringxx(): String =
            StringBuilder().apply {
                var count = 0
                append("[")

                _list.forEach { item ->
                    when (item) {
                        is List,
                        is Create -> append(item.toString())
                        else -> {
                            append("\"")
                            append(item.toString())
                            append("\"")
                        }
                    }

                    if (++count < _list.size)
                        append(", ")
                }

                append("]")
            }.toString()
    //-------------------------------------------------------------------------
}