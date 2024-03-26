package com.example.lifestylehub.utils

import android.content.Context
import java.security.MessageDigest

object AccountManager {
    private const val PREFS_NAME = "Accounts"

    private fun encryptPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.fold("") { str, byte -> str + "%02x".format(byte) }
    }

    fun validateCredentials(context: Context,
                            credentials: Credentials
    ): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val encryptedPassword = encryptPassword(credentials.password)
        val storedPassword = prefs.getString(credentials.login, null)

        return encryptedPassword == storedPassword
    }

    fun saveCredentials(context: Context,
                        credentials: Credentials,
                        onSuccess: () -> Unit,
                        onUserIsExists: () -> Unit
    ) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val existingPassword = prefs.getString(credentials.login, null)
        if (existingPassword != null) {
            onUserIsExists()
            return
        }

        val encryptedPassword = encryptPassword(credentials.password)
        prefs.edit().putString(credentials.login, encryptedPassword).apply()
        onSuccess()
    }

    data class Credentials(val login: String, val password: String)
}