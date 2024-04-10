package core.data.misc.extensions

fun <T> Set<T>.plusOrMinus(elem: T): Set<T> {
    return if (contains(elem)) {
        minus(elem)
    } else {
        plus(elem)
    }
}