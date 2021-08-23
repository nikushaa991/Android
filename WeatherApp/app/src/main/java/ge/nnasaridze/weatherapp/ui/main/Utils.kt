package ge.nnasaridze.weatherapp.ui.main

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object Utils {

    fun loadUrlImage(context: Context, imageView: ImageView, iconUrl: String) {
        Glide.with(context)
            .load("https://openweathermap.org/img/wn/${iconUrl}@2x.png")
            .into(imageView)
    }
}