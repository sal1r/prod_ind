package com.example.lifestylehub.interfaces

import android.content.Context

interface NotesMVP {
    interface View {
        fun showNotesList(notesList: List<Note>)
        fun deleteNote(id: Int)
        fun addNote(note: Note)
        fun getViewContext(): Context
    }

    interface Presenter {
        fun loadNotesList()
        fun onAddButtonClicked(note: Note)
        fun onDeleteButtonClicked(id: Int)
    }

    interface Model {
        fun getNotesList(): List<Note>
        fun addNote(note: Note)
        fun deleteNote(id: Int)
    }

    data class Note(
        val id: Int,
        val name: String,
        val text: String = "",
        val date: String,
        val fsqId: String
    )
}