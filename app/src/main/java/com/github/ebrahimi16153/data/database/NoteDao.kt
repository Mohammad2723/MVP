package com.github.ebrahimi16153.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.ebrahimi16153.data.model.NoteEntity
import com.github.ebrahimi16153.util.Constant
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity): Completable

    @Update
    fun update(note: NoteEntity):Completable

    @Delete
    fun delete(note:NoteEntity):Completable

    @Query("DELETE FROM ${Constant.NOTE_TABLE}")
    fun deleteAll():Completable


    @Query("SELECT * FROM ${Constant.NOTE_TABLE} WHERE id==:id")
    fun getNoteById(id:Int):Observable<NoteEntity>



}