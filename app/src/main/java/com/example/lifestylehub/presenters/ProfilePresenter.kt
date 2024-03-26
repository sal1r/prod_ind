package com.example.lifestylehub.presenters

import com.example.lifestylehub.R
import com.example.lifestylehub.interfaces.ProfileMVP
import com.example.lifestylehub.models.ProfileModel
import com.example.lifestylehub.utils.AccountManager

class ProfilePresenter(private val view: ProfileMVP.View): ProfileMVP.Presenter {
    private val model: ProfileMVP.Model = ProfileModel()

    override fun onCloseButtonClick() {
        view.showScreen(ProfileMVP.Screen.WELCOME, mapOf("HideBackButton" to "true"))
    }

    override fun onRegButtonClick() {
        view.showScreen(ProfileMVP.Screen.REG)
    }

    override fun onLoginButtonClick() {
        view.showScreen(ProfileMVP.Screen.LOGIN)
    }

    override fun onRegSubmitClick(
        credentials: AccountManager.Credentials,
        confirmPassword: String
    ) {
        if (credentials.login.isEmpty() || credentials.password.isEmpty()) return

        if (credentials.password != confirmPassword) {
            view.showScreen(ProfileMVP.Screen.MESSAGE,
                mapOf("message" to view.getViewContext().getString(R.string.reg_passwords_dont_match))
            )
            return
        }

        model.registerUser(view.getViewContext(), credentials,
        {
            view.showScreen(ProfileMVP.Screen.MESSAGE,
                mapOf("message" to view.getViewContext().getString(R.string.reg_success))
            )
        },{
            view.showScreen(ProfileMVP.Screen.MESSAGE,
                mapOf("message" to view.getViewContext().getString(R.string.reg_fail))
            )
        })
    }

    override fun onLoginSubmitClick(credentials: AccountManager.Credentials) {
        model.loginUser(view.getViewContext(), credentials,
            {
                view.showScreen(ProfileMVP.Screen.MESSAGE,
                    mapOf("message" to view.getViewContext().getString(R.string.enter_success),
                        "HideBackButton" to "true"
                    )
                )
            },{
                view.showScreen(ProfileMVP.Screen.MESSAGE,
                    mapOf("message" to view.getViewContext().getString(R.string.enter_fail))
                )
            })
    }
}