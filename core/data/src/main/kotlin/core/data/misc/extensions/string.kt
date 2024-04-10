package core.data.misc.extensions

fun String.ifNotEmpty(): String? = this.takeIf { it.isNotEmpty() }

fun String?.take(max: Int): String? {
    if (this == null) {
        return null
    }
    if (length <= max) {
        return this
    }
    val lastIndex = indexOfAny(arrayListOf(".", "\n\n"), max, true)
    if (lastIndex != -1 && lastIndex < length - 1) {
        return "${substring(0, lastIndex)}…"
    }
    return this
}