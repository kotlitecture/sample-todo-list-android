package app.di.datasource

import app.datasource.config.AppConfigSource
import app.datasource.paging.AppPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesPagingSource {

    @Provides
    @Singleton
    fun sourceWrapped(config: AppConfigSource): AppPagingSource {
        return AppPagingSource(config.getPagingPageSize())
    }

}