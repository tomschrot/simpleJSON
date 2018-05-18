package simpleJSON

typealias ParsedMap  = HashMap<String, Any?>
typealias ParsedList = ArrayList<Any?>
//-------------------------------------------------------------------------
@Suppress("UNCHECKED_CAST")
fun asMap(obj: Any?, block: ParsedMap.() -> Unit) =
        asType(obj, block)
//-------------------------------------------------------------------------
@Suppress("UNCHECKED_CAST")
fun asList(obj: Any?, block: ParsedList.() -> Unit) =
        asType(obj, block)
//-------------------------------------------------------------------------
@Suppress("UNCHECKED_CAST")
private inline fun <T> asType(obj: Any?, block: T.() -> Unit) =
        (obj as T).block()
//-------------------------------------------------------------------------