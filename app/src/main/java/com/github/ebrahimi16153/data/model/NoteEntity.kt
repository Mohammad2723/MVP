package com.github.ebrahimi16153.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ebrahimi16153.util.Constant


@Entity(tableName = Constant.NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String = "",
    val description:String = "",
    val category: String = "",
    val priority:String = ""

)
