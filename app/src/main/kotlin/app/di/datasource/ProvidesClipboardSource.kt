package app.di.datasource

import android.app.Application
import app.datasource.clipboard.AppClipboardSource
import core.data.datasource.clipboard.ClipboardSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesClipboardSource {

    @Provides
    @Singleton
    fun sourceWrapped(app: Application): AppClipboardSource {
        return AppClipboardSource(app)
    }

    @Provides
    @Singleton
    fun source(wrapped: AppClipboardSource): ClipboardSource {
        return wrapped
    }

}