package core.data.datasource.clipboard

import core.data.datasource.DataSource
import kotlinx.coroutines.flow.Flow

/**
 * Provides an interface for accessing clipboard data.
 */
interface ClipboardSource : DataSource {

    /**
     * Retrieves changes made to the clipboard.
     *
     * @return A flow of strings representing clipboard changes.
     */
    fun getChanges(): Flow<String?>

    /**
     * Copies text to the clipboard.
     *
     * @param text The text to be copied to the clipboard.
     * @param label An optional label associated with the copied text.
     */
    fun copy(text: String?, label: String? = null)

}