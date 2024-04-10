package app.di.datasource

import android.app.Application
import app.datasource.database.room.AppRoomSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesRoomSource {

    @Provides
    @Singleton
    fun source(app: Application): AppRoomSource {
        return AppRoomSource(app)
    }

}