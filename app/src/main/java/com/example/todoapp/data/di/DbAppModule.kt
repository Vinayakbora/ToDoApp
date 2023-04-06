package com.example.todoapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.TaskDao
import com.example.todoapp.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbAppModule {

    @Singleton
    @Provides
    fun getAppDB(@ApplicationContext context: Context): TaskDatabase{
        return Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "note_database"
        ).build()
    }

    @Singleton
    @Provides
    fun getDao(appDB: TaskDatabase): TaskDao {
        return appDB.taskDao()
    }
}