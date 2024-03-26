package com.example.lifestylehub.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifestylehub.R
import com.example.lifestylehub.databinding.FragmentHomePageBinding
import com.example.lifestylehub.databinding.FragmentProfilePageBinding
import com.example.lifestylehub.interfaces.ProfileMVP
import com.example.lifestylehub.presenters.ProfilePresenter
import com.example.lifestylehub.utils.AccountManager
import com.example.lifestylehub.utils.PageManager

class ProfilePageFragment : Fragment(), ProfileMVP.View {
    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var pageManager: PageManager
    private val presenter: ProfileMVP.Presenter = ProfilePresenter(this)
    private var initialized = false

    override fun onStart() {
        super.onStart()

        if (!initialized) {
            with(binding) {
                pageManager = PageManager(listOf(
                    clWelcomeScreen.root,
                    clLoginScreen.root,
                    clRegScreen.root,
                    clMessageScreen.root
                ))

                ibBackButton.setOnClickListener { presenter.onCloseButtonClick() }

                clWelcomeScreen.bReg.setOnClickListener { presenter.onRegButtonClick() }
                clWelcomeScreen.bLogin.setOnClickListener { presenter.onLoginButtonClick() }

                clRegScreen.bRegister.setOnClickListener {
                    presenter.onRegSubmitClick(AccountManager.Credentials(
                        login = clRegScreen.tilLogin.editText?.text!!.trim().toString(),
                        password = clRegScreen.tilPassword.editText?.text!!.trim().toString()),
                        clRegScreen.tillCofirmPassword.editText?.text!!.trim().toString()
                    )
                }

                clLoginScreen.bLogin.setOnClickListener {
                    presenter.onLoginSubmitClick(AccountManager.Credentials(
                        login = clLoginScreen.tilLogin.editText?.text!!.trim().toString(),
                        password = clLoginScreen.tilPassword.editText?.text!!.trim().toString()))
                }

                initialized = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun showScreen(screen: ProfileMVP.Screen, params: Map<String, String>) {
        // костыль
        if (screen == ProfileMVP.Screen.MESSAGE)
            binding.clMessageScreen.tvMessage.text = params["message"]

        binding.ibBackButton.visibility =
            if (params["HideBackButton"] == "true") View.GONE else View.VISIBLE

        pageManager.showPage(screen.ordinal)
    }

    override fun getViewContext(): Context = requireContext()
}