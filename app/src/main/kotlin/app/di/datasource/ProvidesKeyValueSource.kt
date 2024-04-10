package app.di.datasource

import android.app.Application
import app.datasource.keyvalue.AppKeyValueSource
import core.data.datasource.keyvalue.KeyValueSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesKeyValueSource {

    @Provides
    @Singleton
    fun sourceWrapped(app: Application): AppKeyValueSource {
        return AppKeyValueSource(app)
    }

    @Provides
    @Singleton
    fun source(wrapped: AppKeyValueSource): KeyValueSource {
        return wrapped
    }

}