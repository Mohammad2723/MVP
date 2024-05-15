package com.github.ebrahimi16153.util.di

import android.content.Context
import androidx.room.Room
import com.github.ebrahimi16153.data.database.NoteDataBase
import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoteDataBase::class.java, Constant.NOTE_DATABASE)
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideDao(db:NoteDataBase) = db.dao

    @Singleton
    @Provides
    fun provideNoteEntity() = NoteEntity()

}