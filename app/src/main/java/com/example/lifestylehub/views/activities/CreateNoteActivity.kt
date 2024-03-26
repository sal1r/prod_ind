package com.example.lifestylehub.views.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.lifestylehub.databinding.ActivityCreateNoteBinding
import com.example.lifestylehub.important.Importants
import com.example.lifestylehub.interfaces.NotesMVP
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tilName.editText?.setText(intent.getStringExtra("name")!!)

        with(binding) {
            bCreate.setOnClickListener {
                val note = NotesMVP.Note(
                    id = 0,
                    name = binding.tilName.editText?.text?.trim().toString(),
                    text = binding.tilText.editText?.text?.trim().toString(),
                    date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        .format(Calendar.getInstance().time),
                    fsqId = intent.getStringExtra("fsqId")!!
                )
                Importants.notesPresenter!!.onAddButtonClicked(note)
                finish()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}