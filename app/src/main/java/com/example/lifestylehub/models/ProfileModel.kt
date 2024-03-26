package com.example.lifestylehub.models

import android.content.Context
import com.example.lifestylehub.interfaces.ProfileMVP
import com.example.lifestylehub.utils.AccountManager

class ProfileModel: ProfileMVP.Model {
    override fun registerUser(
        context: Context,
        credentials: AccountManager.Credentials,
        onSuccess: () -> Unit,
        onUserIsExists: () -> Unit
    ) {
        AccountManager.saveCredentials(context, credentials, onSuccess, onUserIsExists)
    }

    override fun loginUser(
        context: Context,
        credentials: AccountManager.Credentials,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        if (AccountManager.validateCredentials(context, credentials)) onSuccess()
        else onFailure()
    }
}