package com.example.lifestylehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifestylehub.R
import com.example.lifestylehub.databinding.PlaceAdditionalImageBinding
import com.example.lifestylehub.utils.ImageLoader

class OtherImagesAdapter(images: List<String>): RecyclerView.Adapter<OtherImagesAdapter.MyHolder>() {
    private val data = images
    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = PlaceAdditionalImageBinding.bind(view)
        fun bind(image: String) {
            ImageLoader.to(binding.root).load(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_additional_image, parent, false)

        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}