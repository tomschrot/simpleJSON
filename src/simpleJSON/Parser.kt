package simpleJSON
/**
 * This parser reads a JSON string into a interleaved Kotlin hash map.
 * The original idea was taken from https://wesleytsai.io/2015/06/13/a-json-parser/, thx.
 * It does not cover the complete JSON features and handles string values only!
 * I use it for inter process communication and that works great.
 *
 * @author Tom Schröter
 *
 */
object Parser {
    //-------------------------------------------------------------------------
    private enum class Delimiters(val char: Char) {
        DOUBLEQUOTE('\"'),
        BRACE_OPEN('{'),
        BRACE_CLOSE('}'),
        BRACKET_OPEN('['),
        BRACKET_CLOSE(']'),
        COMMA(','),
        COLON(':'),
        SPACE(' '),
        NEWLINE('\n'),
        CR('\r')
    }
    //-------------------------------------------------------------------------
    private var s = ""
    private var len = 0
    private var index = 0
    //-------------------------------------------------------------------------
    @Suppress("UNCHECKED_CAST")
    infix fun convert(str: String): ParsedMap {
        s       = str
        len     = s.length
        index   = 0

        moveTo(Delimiters.BRACE_OPEN.char)
        return parse() as ParsedMap
    }
    //-------------------------------------------------------------------------
    private val position: Char
        get() = s[index]

    private fun next() =
            (++index < len)

    private fun moveTo(stop: Char = Delimiters.SPACE.char) {
        while (position != stop && next());
    }
    //-------------------------------------------------------------------------
    private fun skipWhite() {
        while (next() && isWhitespace);
    }

    private inline val isWhitespace: Boolean
        get() =
            when (position) {
                Delimiters.CR.char,
                Delimiters.SPACE.char,
                Delimiters.NEWLINE.char -> true
                else                    -> false
            }
    //-------------------------------------------------------------------------
    private fun parse(): Any? =
        when (position) {
            Delimiters.BRACE_OPEN.char   -> obj()
            Delimiters.DOUBLEQUOTE.char  -> str()
            Delimiters.BRACKET_OPEN.char -> arr()
            'n', 'N'                     -> nul()
            else                         -> null
        }
    //-------------------------------------------------------------------------
    private fun str() =
            StringBuilder().apply {
                while (next() && position != Delimiters.DOUBLEQUOTE.char)
                    append(position)
            }.toString()
    //-------------------------------------------------------------------------
    private inline val testForObject
            get()  = (
                position == Delimiters.COMMA.char &&    // comma separated for more
                position != Delimiters.BRACE_CLOSE.char // until closing '}'
            )

    private fun obj(): ParsedMap =
            ParsedMap().apply {
                do {
                    moveTo(Delimiters.DOUBLEQUOTE.char)
                    val key = str()
                    moveTo(Delimiters.COLON.char)
                    skipWhite()
                    this[key] = parse()
                    skipWhite()
                } while ( testForObject )
            }
    //-------------------------------------------------------------------------
    private inline val testForArray
        get()  = (
            position == Delimiters.COMMA.char &&        // comma separated for more
            position != Delimiters.BRACKET_CLOSE.char   // until closing ']'
        )

    private fun arr(): ParsedList =
            ParsedList().apply {
                do{
                    skipWhite()
                    add(parse())
                    skipWhite()
                }while ( testForArray )
            }
    //-------------------------------------------------------------------------
    private fun nul(): Any? {
        index += 3
        return null
    }
    //-------------------------------------------------------------------------
}

