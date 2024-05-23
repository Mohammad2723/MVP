package com.github.ebrahimi16153.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ebrahimi16153.util.Constant


@Entity(tableName = Constant.NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var title:String = "",
    var description:String = "",
    var category: String = "",
    var priority:String = ""

)
