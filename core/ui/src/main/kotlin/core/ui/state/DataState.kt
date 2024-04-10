package core.ui.state

/**
 * Represents the state of data loading.
 */
sealed class DataState {

    val uid = System.nanoTime()

    data class Loading(
        val id: String
    ) : DataState()

    data class Loaded(
        val id: String
    ) : DataState()

    data class Error(
        val id: String,
        val th: Throwable
    ) : DataState()
}