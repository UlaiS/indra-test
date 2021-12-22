package mx.ulai.indra.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import mx.ulai.indra.util.Constants.BASE_URL_IMAGE
import mx.ulai.indra.util.setImageGlide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("switchVisible")
    fun switchVisible(view: View, show: Boolean){
        view.visibility = if(show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, @Nullable url: String?){
        view.setImageGlide(BASE_URL_IMAGE + url)
    }

    @JvmStatic
    @BindingAdapter("setVideo")
    fun setVideo(view: YouTubePlayerView, @Nullable url: String?){
        view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            val videoId: String? = url
            if (videoId != null) {
                youTubePlayer.loadVideo(videoId, 0F)
            }
        }
    })

    }

    @JvmStatic
    @BindingAdapter("setDoubleToText")
    fun setDoubleToText(view: TextView, number: Double){
        view.text = number.toString()
    }

    @JvmStatic
    @BindingAdapter("setProgress")
    fun setProgress(view: CircularProgressIndicator, number: Double){
        view.progress = (number * 10).toInt()
    }
}