package mx.ulai.indra.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

open class YoutubeAdapter(val context: Context, val key: String, val view: YouTubePlayerView): YouTubePlayer.OnInitializedListener {
    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        if (!p2) p1?.cueVideo(key)
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        if(p1?.isUserRecoverableError == true){
            p1.getErrorDialog(context as Activity, 1).show()
        }else{
            val error: String = "Error al inicializar YouTube "+ p1.toString()
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun getYouTubePlayerProvider(): YouTubePlayer.Provider = view
}