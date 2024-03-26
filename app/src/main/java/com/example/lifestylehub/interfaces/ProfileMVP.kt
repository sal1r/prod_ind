package com.example.lifestylehub.interfaces

import android.content.Context
import com.example.lifestylehub.utils.AccountManager
import java.net.PasswordAuthentication
import kotlin.reflect.KProperty

interface ProfileMVP {
    interface View {
        // тут бы с типизацией что-то сделать, да так, чтобы красиво было
        fun showScreen(screen: Screen, params: Map<String, String> = emptyMap())
        fun getViewContext(): Context
    }

    interface Presenter {
        fun onCloseButtonClick()
        fun onRegButtonClick()
        fun onLoginButtonClick()
        fun onRegSubmitClick(credentials: AccountManager.Credentials, confirmPassword: String)
        fun onLoginSubmitClick(credentials: AccountManager.Credentials)
    }

    interface Model {
        fun registerUser(context: Context,
                         credentials: AccountManager.Credentials,
                         onSuccess: () -> Unit,
                         onUserIsExists: () -> Unit)
        fun loginUser(context: Context,
                      credentials: AccountManager.Credentials,
                      onSuccess: () -> Unit,
                      onFailure: () -> Unit)
    }

    enum class Screen {
        WELCOME,
        LOGIN,
        REG,
        MESSAGE
    }
}