package com.example.lifestylehub.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifestylehub.adapters.FeedAdapter
import com.example.lifestylehub.databinding.FragmentHomePageBinding
import com.example.lifestylehub.interfaces.FeedMVP
import com.example.lifestylehub.presenters.FeedPresenter
import com.example.lifestylehub.utils.LocationObserver
import com.example.lifestylehub.utils.LocationUtiles

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var presenter: FeedMVP.Presenter
    private var dataIsLoaded = false

    override fun onStart() {
        super.onStart()

        if (!dataIsLoaded) {
            presenter = FeedPresenter(binding.rvFeed)
            LocationObserver.addListener {
                LocationUtiles.runWithLL(requireContext(), presenter::loadPlaces,
                    {
                        binding.tvNoPlacesNear.visibility = View.VISIBLE
                    }, {
                        binding.tvNoPlacesNear.visibility = View.VISIBLE
                    }
                )
            }
            dataIsLoaded = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(layoutInflater)
        return binding.root
    }
}