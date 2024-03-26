package com.example.lifestylehub.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lifestylehub.databinding.NoteViewBinding
import com.example.lifestylehub.interfaces.NotesMVP
import com.example.lifestylehub.views.activities.PlacesDetailActivity
import com.example.lifestylehub.views.ui.NoteView
import kotlin.properties.Delegates

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.MyHolder>() {
    private val notes: MutableList<NotesMVP.Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = NoteView(parent.context)

        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    @SuppressLint("NotifyDataSetChanged")
    fun fillNotes(notes: List<NotesMVP.Note>) {
        this.notes += notes
        notifyDataSetChanged()
    }

    fun addNote(note: NotesMVP.Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteNote(id: Int) {
        notes.removeIf { it.id == id }
        notifyDataSetChanged()
    }

    class MyHolder(view: View): ViewHolder(view) {
        private val binding = NoteViewBinding.bind(view)
        var id = 0

        fun bind(note: NotesMVP.Note) {
            id = note.id

            with(binding) {
                root.setOnClickListener {
                    if (note.fsqId.isEmpty()) return@setOnClickListener
                    val intent = Intent(itemView.context, PlacesDetailActivity::class.java)
                    intent.putExtra("fsqId", note.fsqId)
                    itemView.context.startActivity(intent)
                }

                tvName.text = note.name
                tvDate.text = note.date
                tvText.text = note.text
            }
        }
    }
}