package app.di.datasource

import app.datasource.config.AppConfigSource
import core.data.datasource.config.ConfigSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesConfigSource {

    @Provides
    @Singleton
    fun sourceWrapped(): AppConfigSource {
        return AppConfigSource()
    }

    @Provides
    @Singleton
    fun source(wrapped: AppConfigSource): ConfigSource {
        return wrapped
    }

}