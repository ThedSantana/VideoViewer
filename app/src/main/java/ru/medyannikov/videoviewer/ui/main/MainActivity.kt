package ru.medyannikov.videoviewer.ui.main

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.widget.Toolbar
import butterknife.BindView
import ru.medyannikov.videoviewer.R
import ru.medyannikov.videoviewer.ui.base.BaseActivity
import ru.medyannikov.videoviewer.R.id.video_view
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import android.media.tv.TvTrackInfo.TYPE_VIDEO
import android.media.MediaCodec
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener
import com.google.android.exoplayer2.upstream.DataSpec
import java.io.IOException


class MainActivity : BaseActivity() {
  @BindView(R.id.video_view)
  lateinit var simpleExoPlayerView: SimpleExoPlayerView

  @BindView(R.id.toolbar)
  lateinit var toolbar: Toolbar

  val mainHandler = Handler()

  override fun getLayout() = R.layout.a_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initToolbar()
    initViews()
  }


  private fun initToolbar() {
    if (supportActionBar != null) {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun initViews() {
    simpleExoPlayerView.requestFocus()
    val bandwidthMeter = DefaultBandwidthMeter()
    val mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "videoViewer"),bandwidthMeter )

    val videoTrackSelectionFactory = AdaptiveVideoTrackSelection.Factory(bandwidthMeter)
    val trackSelector = DefaultTrackSelector(mainHandler, videoTrackSelectionFactory)
    val player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, DefaultLoadControl(),
        null, true)
    simpleExoPlayerView.player = player
    player.playWhenReady = true
    val mediaSource = HlsMediaSource(Uri.parse("http://195.208.64.40/hls_tv/tv.m3u8"),
        mediaDataSourceFactory, mainHandler, object: AdaptiveMediaSourceEventListener {
      override fun onLoadStarted(dataSpec: DataSpec?, dataType: Int, trackType: Int, trackFormat: Format?,
          trackSelectionReason: Int, trackSelectionData: Any?, mediaStartTimeMs: Long, mediaEndTimeMs: Long,
          elapsedRealtimeMs: Long) {

      }

      override fun onDownstreamFormatChanged(trackType: Int, trackFormat: Format?, trackSelectionReason: Int,
          trackSelectionData: Any?, mediaTimeMs: Long) {
      }

      override fun onUpstreamDiscarded(trackType: Int, mediaStartTimeMs: Long, mediaEndTimeMs: Long) {
      }

      override fun onLoadCanceled(dataSpec: DataSpec?, dataType: Int, trackType: Int, trackFormat: Format?,
          trackSelectionReason: Int, trackSelectionData: Any?, mediaStartTimeMs: Long, mediaEndTimeMs: Long,
          elapsedRealtimeMs: Long, loadDurationMs: Long, bytesLoaded: Long) {
      }

      override fun onLoadCompleted(dataSpec: DataSpec?, dataType: Int, trackType: Int, trackFormat: Format?,
          trackSelectionReason: Int, trackSelectionData: Any?, mediaStartTimeMs: Long, mediaEndTimeMs: Long,
          elapsedRealtimeMs: Long, loadDurationMs: Long, bytesLoaded: Long) {
      }

      override fun onLoadError(dataSpec: DataSpec?, dataType: Int, trackType: Int, trackFormat: Format?,
          trackSelectionReason: Int, trackSelectionData: Any?, mediaStartTimeMs: Long, mediaEndTimeMs: Long,
          elapsedRealtimeMs: Long, loadDurationMs: Long, bytesLoaded: Long, error: IOException?, wasCanceled: Boolean) {
      }
    })
    /*val smooth = HlsMediaSource(Uri.parse("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"),
        mediaDataSourceFactory,mainHandler,null)
    val extractorsFactory = DefaultExtractorsFactory()
    // http://s-s1-17-eeef.kinogo.club/f29ea665077468c5b09d2fea4e9596b9_ODguMjAwLjIyMS42Mw==/serials/teoriya-bolshogo-vzryva/9-sezon/tbv-9-01_kurazh.flv
    val mediaSource =  ExtractorMediaSource(Uri.parse("https://videos.sports.ru/cdn/video/RU/0/5f/1045836187/480p.mp4"),
        mediaDataSourceFactory, extractorsFactory, null, null)*/
    player.prepare(mediaSource)
    player.playWhenReady = true
  }

}
