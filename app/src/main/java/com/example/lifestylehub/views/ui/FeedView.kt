package com.example.lifestylehub.views.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifestylehub.adapters.FeedAdapter
import com.example.lifestylehub.interfaces.FeedMVP

class FeedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), FeedMVP.View {

    init {
        adapter = FeedAdapter()
        layoutManager = LinearLayoutManager(context)
    }

    override fun addPlaces(places: List<FeedMVP.Place>) {
        (adapter as FeedAdapter).addItems(places)
    }

    override fun runInUiThread(runnable: Runnable) { post(runnable) }
}