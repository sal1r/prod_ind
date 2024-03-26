package com.example.lifestylehub.views.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.lifestylehub.R
import com.example.lifestylehub.databinding.NoteViewBinding

class NoteView @JvmOverloads constructor(
    context: Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    private var binding: NoteViewBinding

    init {
        LayoutInflater.from(context).inflate(R.layout.note_view, this, true)
        binding = NoteViewBinding.bind(this)
    }
}