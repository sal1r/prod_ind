package com.example.lifestylehub.views.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifestylehub.R
import com.example.lifestylehub.adapters.NotesAdapter
import com.example.lifestylehub.databinding.FragmentMyLeisurePageBinding
import com.example.lifestylehub.important.Importants
import com.example.lifestylehub.interfaces.NotesMVP
import com.example.lifestylehub.presenters.NotesPresenter
import com.example.lifestylehub.views.activities.CreateNoteActivity

class MyLeisurePageFragment : Fragment(), NotesMVP.View {
    private lateinit var binding: FragmentMyLeisurePageBinding
    private lateinit var presenter: NotesMVP.Presenter
    private var dataIsLoaded = false
    private val notesAdapter = NotesAdapter()

    override fun onStart() {
        super.onStart()

        if (!dataIsLoaded) {
            presenter = NotesPresenter(this)
            Importants.notesPresenter = presenter

            ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean = false

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        presenter.onDeleteButtonClicked((viewHolder as NotesAdapter.MyHolder).id)
                    }
                }
            ).attachToRecyclerView(binding.rvNotes)

            with(binding) {
                rvNotes.adapter = notesAdapter
                rvNotes.layoutManager = LinearLayoutManager(context)
                clAddButton.setOnClickListener {
                    val intent = Intent(
                        context,
                        CreateNoteActivity::class.java
                    )
                    intent.putExtra("fsqId", "")
                    intent.putExtra("name", "")
                    startActivity(intent)
                }
            }
            presenter.loadNotesList()
            dataIsLoaded = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyLeisurePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun showNotesList(notesList: List<NotesMVP.Note>) {
        notesAdapter.fillNotes(notesList)
    }

    override fun deleteNote(id: Int) {
        notesAdapter.deleteNote(id)
    }

    override fun addNote(note: NotesMVP.Note) {
        notesAdapter.addNote(note)
    }

    override fun getViewContext(): Context = requireContext()
}