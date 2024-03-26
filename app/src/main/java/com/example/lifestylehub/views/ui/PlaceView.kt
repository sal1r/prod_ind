package com.example.lifestylehub.views.ui

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.lifestylehub.R
import com.example.lifestylehub.databinding.PlaceViewBinding
import com.example.lifestylehub.views.activities.PlacesDetailActivity

class PlaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: PlaceViewBinding
    var fsqId: String = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.place_view, this, true)
        binding = PlaceViewBinding.bind(this)

        binding.root.setOnClickListener {
            val intent = Intent(context, PlacesDetailActivity::class.java)
            intent.putExtra("fsqId", fsqId)
            context.startActivity(intent)
        }
    }
}