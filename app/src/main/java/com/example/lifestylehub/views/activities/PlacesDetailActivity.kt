package com.example.lifestylehub.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifestylehub.adapters.OtherImagesAdapter
import com.example.lifestylehub.databinding.ActivityPlacesDetailBinding
import com.example.lifestylehub.interfaces.PlaceDetailMVP
import com.example.lifestylehub.presenters.PlaceDetailPresenter
import com.example.lifestylehub.utils.ImageLoader

class PlacesDetailActivity : AppCompatActivity(), PlaceDetailMVP.View {
    private lateinit var binding: ActivityPlacesDetailBinding
    private var presenter: PlaceDetailMVP.Presenter = PlaceDetailPresenter(this)
    private lateinit var fsqId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fsqId = intent.getStringExtra("fsqId")!!
        presenter.loadPlaceDetail(fsqId)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun showPlaceDetail(placeDetail: PlaceDetailMVP.PlaceDetail) {
        with(binding) {
            ImageLoader.to(ivMainImage).load(placeDetail.mainImage)
            tvName.text = placeDetail.name
            tvAddress.text = placeDetail.address

            placeDetail.categories.forEach {
                tvCategories.text = tvCategories.text.toString() + it + "\n"
            }

            rvOtherImages.adapter = OtherImagesAdapter(placeDetail.otherImages)
            rvOtherImages.layoutManager = LinearLayoutManager(
                this@PlacesDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false)

            bAdd.setOnClickListener {
                val intent = Intent(
                    this@PlacesDetailActivity,
                    CreateNoteActivity::class.java
                )
                intent.putExtra("fsqId", fsqId)
                intent.putExtra("name", placeDetail.name)
                startActivity(intent)
            }
        }
    }

    override fun runInUiThread(runnable: Runnable) { runOnUiThread(runnable) }
}