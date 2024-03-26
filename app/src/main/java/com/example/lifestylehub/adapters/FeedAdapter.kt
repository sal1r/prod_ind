package com.example.lifestylehub.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifestylehub.api.PlacesApi
import com.example.lifestylehub.databinding.PlaceViewBinding
import com.example.lifestylehub.interfaces.FeedMVP
import com.example.lifestylehub.utils.ImageLoader
import com.example.lifestylehub.views.ui.PlaceView

class FeedAdapter: RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    private val feedItems: MutableList<FeedMVP.FeedItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        when (feedItems[position]) {
            is FeedMVP.Place -> return 0
        }

        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        when (viewType) {
            0 -> { // FeedMVP.Place
                val view = PlaceView(parent.context)
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                return PlaceViewHolder(view, viewType)
            }
        }

        throw IllegalArgumentException("Invalid view type")
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        when (holder.viewType) {
            0 -> { // FeedMVP.Place
                holder.bind(feedItems[position])
            }
        }
    }

    override fun getItemCount(): Int = feedItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<FeedMVP.FeedItem>) {
        feedItems += items
        notifyDataSetChanged()
    }

    abstract class FeedViewHolder(view: View, val viewType: Int) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: FeedMVP.FeedItem)
    }

    class PlaceViewHolder(view: View, viewType: Int) : FeedViewHolder(view, viewType) {
        private val binding = PlaceViewBinding.bind(view)

        override fun bind(item: FeedMVP.FeedItem) {
            val place = item as FeedMVP.Place

            (itemView as PlaceView).fsqId = place.fsqId

            with(binding) {
                ImageLoader.to(ivImage)
                    .header("Authorization", PlacesApi.API_KEY)
                    .load(place.image)
                tvName.text = place.name
                tvAddress.text = place.address
            }
        }
    }
}