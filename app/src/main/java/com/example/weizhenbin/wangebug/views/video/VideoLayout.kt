package com.example.weizhenbin.wangebug.views.video

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.media.MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.*
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.tools.CommonTool
import com.example.weizhenbin.wangebug.tools.PhoneTool
import kotlin.math.min

/**
 * Created by weizhenbin on 2019/1/8.
 */
class VideoLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr), VideoPlayController, MediaPlayer.OnPreparedListener,
        TextureView.SurfaceTextureListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnBufferingUpdateListener ,MediaPlayer.OnInfoListener, View.OnClickListener{


    private var multiple = 1.0
    private var videoView: TextureView
    private var llController: LinearLayout
    private var sbProgress: SeekBar
    private var mediaPlayer: MediaPlayer
    private var surfaceTexture: SurfaceTexture? = null
    private var mSurface: Surface? = null
    private var path: String = "http://mvideo.spriteapp.cn/video/2018/1211/d64da792-fcfb-11e8-8daa-d4ae5296039d_wpc.mp4"
    private var tvCurrentProgress:TextView
    private var tvTotalProgress:TextView
    private var ivPlayController:ImageView
    private var isTrackingTouch=false
    init {
        LayoutInflater.from(context).inflate(R.layout.video_layout, this)
        videoView = findViewById(R.id.video_view)
        llController = findViewById(R.id.ll_controller)
        sbProgress = findViewById(R.id.sb_progress)
        tvCurrentProgress=findViewById(R.id.tv_current_progress)
        tvTotalProgress=findViewById(R.id.tv_total_progress)
        ivPlayController=findViewById(R.id.iv_play_controller)
        mediaPlayer = MediaPlayer()
        videoView.surfaceTextureListener = this
        sbProgress.setOnSeekBarChangeListener(this)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setScreenOnWhilePlaying(true)
        ivPlayController.setOnClickListener(this)
    }


    override fun start() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun setDataSource(path: String) {
        this.path = path
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.apply {
            start()
            changeSize()
            setPlayStatusIcon(isPlaying)
        }
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d("VideoLayout","onDetachedFromWindow")
        surfaceTexture?.run {
            release()
        }
        mSurface?.run {
            release()
        }
        surfaceTexture=null
        mSurface=null
      //  mediaPlayer.release()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d("VideoLayout","onAttachedToWindow")
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {


    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        if (!isTrackingTouch) {
            sbProgress.max = mediaPlayer.duration
            sbProgress.progress = mediaPlayer.currentPosition
            tvTotalProgress.text=CommonTool.durationFormat(mediaPlayer.duration.toLong())
            tvCurrentProgress.text=CommonTool.durationFormat(mediaPlayer.currentPosition.toLong())
        }
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mediaPlayer.apply {
            if (isPlaying) {
                pause()
                setPlayStatusIcon(isPlaying)
            }
        }
        return false
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        mediaPlayer.apply {
            if (surfaceTexture == null) {
                surfaceTexture = surface
                if (mSurface == null) {
                    mSurface = Surface(surfaceTexture)
                }
                reset()
                setSurface(mSurface)
                setDataSource(path)
                prepareAsync()
            } else {
                videoView.surfaceTexture = surfaceTexture
                start()
                setPlayStatusIcon(isPlaying)
            }
        }
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (isTrackingTouch) {
            mediaPlayer.seekTo(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        isTrackingTouch=true
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        isTrackingTouch=false
    }


    override fun onSeekComplete(mp: MediaPlayer?) {
        mp?.apply {
           if (!isPlaying){
               start()
           }
            tvCurrentProgress.text=CommonTool.durationFormat(currentPosition.toLong())
            setPlayStatusIcon(isPlaying)
        }
    }
    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        sbProgress.secondaryProgress= (percent/100.toFloat()*mp!!.duration).toInt()
    }
    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d("VideoLayout","what: $what")
        when(what){
            MEDIA_INFO_VIDEO_RENDERING_START->{
                mp?.apply {
                    setPlayStatusIcon(isPlaying)
                }
            }
        }
       return true
    }

    override fun onClick(v: View?) {
        if (v==ivPlayController){
            mediaPlayer.apply {
                if (isPlaying){
                    this@VideoLayout.pause()
                }else{
                    this@VideoLayout.start()
                }
                setPlayStatusIcon(isPlaying)
            }
        }
    }

    private fun setPlayStatusIcon(isPlaying:Boolean){
            if (isPlaying){
                ivPlayController.setImageResource(R.drawable.video_pause)
            }else{
                ivPlayController.setImageResource(R.drawable.video_play)
            }
    }

     fun changeSize() {
        multiple = min(PhoneTool.screenWidth.toDouble() / mediaPlayer.videoWidth.toDouble(), PhoneTool.screenHeight.toDouble() / mediaPlayer.videoHeight.toDouble())
        val textTextureParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams((mediaPlayer.videoWidth * multiple).toInt(), (mediaPlayer.videoHeight * multiple).toInt())
        textTextureParams.addRule(CENTER_IN_PARENT)
        videoView.layoutParams = textTextureParams
        videoView.requestLayout()
    }
}