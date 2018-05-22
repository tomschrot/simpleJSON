package simpleJSON

class List() {
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
    override fun toString(): String =
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