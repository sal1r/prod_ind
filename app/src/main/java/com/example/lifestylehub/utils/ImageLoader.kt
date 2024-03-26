package com.example.lifestylehub.utils

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ImageLoader {
    private val client = okhttp3.OkHttpClient()
    private val cache: MutableMap<String, Bitmap> = mutableMapOf()

    fun to(view: ImageView): Loader {
        return Loader(view)
    }

    class Loader(
        private val view: ImageView
    ) {
        private val headers: MutableMap<String, String> = mutableMapOf()

        fun load(url: String) {
            if (cache.containsKey(url)) {
                view.setImageBitmap(cache[url]!!)
                return
            }

            CoroutineScope(Dispatchers.IO).launch {
                val request = okhttp3.Request.Builder().url(url).build()
                try {
                    val response = client.newCall(request).execute()
                    val bitmap = BitmapFactory.decodeStream(response.body!!.byteStream())

                    view.post {
                        cache[url] = bitmap
                        view.setImageBitmap(bitmap)
                        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                        animator.duration = 500
                        animator.start()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun header(name: String, value: String): Loader {
            headers[name] = value
            return this
        }
    }
}