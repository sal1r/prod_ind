package com.example.lifestylehub.models

import android.content.Context
import android.util.Log
import com.example.lifestylehub.interfaces.NotesMVP

class NotesModel(context: Context): NotesMVP.Model {
    private val db= context.openOrCreateDatabase(
        "notes.db", Context.MODE_PRIVATE, null
    )

    init {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS notes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                txt TEXT,
                date TEXT,
                fsqId TEXT);
        """)
    }

    override fun getNotesList(): List<NotesMVP.Note> {
        val cur = db.rawQuery("SELECT * FROM notes;", null)
        val notes = mutableListOf<NotesMVP.Note>()

        while (cur.moveToNext()) {
            val id = cur.getInt(0)
            val name = cur.getString(1)
            val text = cur.getString(2)
            val date = cur.getString(3)
            val fsqId = cur.getString(4)
            notes.add(NotesMVP.Note(id, name, text, date, fsqId))
        }

        cur.close()

        return notes
    }

    override fun addNote(note: NotesMVP.Note) {
        db.execSQL("""
            INSERT INTO notes(name, txt, date, fsqId)
            VALUES("${note.name}", "${note.text}", "${note.date}", "${note.fsqId}");
        """)
    }

    override fun deleteNote(id: Int) {
        db.execSQL("DELETE FROM notes WHERE id = $id;")
    }
}