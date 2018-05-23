package simpleJSON

/**
 * Combine common string handling for map & list.
 *
 * @author Thomas Schröter
 */
internal interface JointString {

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
