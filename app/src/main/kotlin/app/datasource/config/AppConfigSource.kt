package app.datasource.config

import core.data.datasource.config.DelegateConfigSource

/**
 * This class represents a configuration source for application settings.
 */
class AppConfigSource : DelegateConfigSource(
) {
    fun getSearchDebounce(): Long = getLong("ui_search_debounce") { 500 }
    fun getPagingPageSize(): Int = getInt("paging_page_size") { 30 }
    fun getUiLoadingDelay(): Long = getLong("ui_loading_delay") { 50 }
    fun getUiLoadingTimeout(): Long = getLong("ui_loading_timeout") { 30_000 }
}
