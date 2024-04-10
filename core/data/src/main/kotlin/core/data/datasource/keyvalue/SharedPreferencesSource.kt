package core.data.datasource.keyvalue

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * Key-value data source implementation using SharedPreferences.
 *
 * @param app The application context.
 */
open class SharedPreferencesSource(
    protected val app: Application,
    private val name: String = "key_value_source"
) : BaseSharedPreferencesSource(), KeyValueSource {

    override fun createSharedPreferences(): SharedPreferences {
        return app.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

}