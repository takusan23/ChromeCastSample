package io.github.takusan23.chromecastsample

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.MediaMetadata.MEDIA_TYPE_MOVIE
import com.google.android.gms.cast.framework.*
import com.google.android.gms.common.images.WebImage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var castContext: CastContext
    lateinit var sessionManagerListener: SessionManagerListener<CastSession>

    val uri = "https://pb0330f25d8.dmc.nico/hlslive/ht2_nicolive/nicolive-production-pg26302778180174_63cc95d8a7a3b4706d7662fbb338a2b3568dc91d2c481ebb6add4ff8b6e10dca/master.m3u8?ht2_nicolive=40210583.g8pdj7_q3h86l_2aool9gmbjrld"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        castContext = CastContext.getSharedInstance(this)

        sessionManagerListener = object : SessionManagerListener<CastSession> {
            override fun onSessionStarted(p0: CastSession?, p1: String?) {
                button2.setOnClickListener {
                    val movieMetadata = MediaMetadata(MEDIA_TYPE_MOVIE)
                    movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, "sub title")
                    movieMetadata.putString(MediaMetadata.KEY_TITLE, "title")
                    movieMetadata.addImage(WebImage(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/480x270/DesigningForGoogleCast2-480x270.jpg")))
                    movieMetadata.addImage(WebImage(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/780x1200/DesigningForGoogleCast-887x1200.jpg")))
                    val mediaInfo =
                        MediaInfo.Builder(uri)
                            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                            .setContentType("videos/mp4")
                            .setMetadata(movieMetadata)
                            .build()
                    val mediaLoadRequestData = MediaLoadRequestData.Builder()
                        .setMediaInfo(mediaInfo)
                        .build()
                    val remoteMediaClient = p0?.remoteMediaClient
                    remoteMediaClient?.load(mediaLoadRequestData)
                }
            }

            override fun onSessionResumeFailed(p0: CastSession?, p1: Int) {

            }

            override fun onSessionSuspended(p0: CastSession?, p1: Int) {

            }

            override fun onSessionEnded(p0: CastSession?, p1: Int) {

            }

            override fun onSessionResumed(p0: CastSession?, p1: Boolean) {

            }

            override fun onSessionStarting(p0: CastSession?) {

            }

            override fun onSessionResuming(p0: CastSession?, p1: String?) {

            }

            override fun onSessionEnding(p0: CastSession?) {

            }

            override fun onSessionStartFailed(p0: CastSession?, p1: Int) {

            }


        }
    }

    override fun onResume() {
        super.onResume()
        castContext.sessionManager.addSessionManagerListener(
            sessionManagerListener,
            CastSession::class.java
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item)
        return true
    }

    override fun onPause() {
        super.onPause()
        castContext.sessionManager.removeSessionManagerListener(
            sessionManagerListener,
            CastSession::class.java
        )
    }


}
