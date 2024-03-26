package com.example.lifestylehub.presenters

import com.example.lifestylehub.interfaces.NotesMVP
import com.example.lifestylehub.models.NotesModel

class NotesPresenter(private val view: NotesMVP.View): NotesMVP.Presenter {
    private val model: NotesMVP.Model = NotesModel(view.getViewContext())

    override fun loadNotesList() {
        val notes = model.getNotesList()
        view.showNotesList(notes)
    }

    override fun onAddButtonClicked(note: NotesMVP.Note) {
        model.addNote(note)
        view.addNote(note)
    }

    override fun onDeleteButtonClicked(id: Int) {
        model.deleteNote(id)
        view.deleteNote(id)
    }
}