package com.android.vlcdemo;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.vlcdemo.utils.LogUtils;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class CustomPlayerActivity extends AppCompatActivity implements MediaPlayer.EventListener, IVLCVout.OnNewVideoLayoutListener {

    private static final String ASSET_FILENAME = "love.mkv";

    private SurfaceView mSurfaceView;
    private String mPlayerUrl;

    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutom_player);

        mPlayerUrl = getIntent().getStringExtra("playerUrl");
        LogUtils.i("CustomPlayerActivity playerUrl = " + mPlayerUrl);

        initViews();
        initPlayer();
    }

    private void initViews() {
        mSurfaceView = (SurfaceView) findViewById(R.id.video_surface_view);
    }

    private void initPlayer() {
        final ArrayList<String> args = new ArrayList<>();
        args.add("--vout=android-display");
        mLibVLC = new LibVLC(this, args);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        mMediaPlayer.setEventListener(this);

        IVLCVout vlcVout = mMediaPlayer.getVLCVout();
        vlcVout.setVideoView(mSurfaceView);
        vlcVout.attachViews(this);

        try {
            Media media = null;
            if (TextUtils.isEmpty(mPlayerUrl)) {
                media = new Media(mLibVLC, getAssets().openFd(ASSET_FILENAME));
            } else {
                media = new Media(mLibVLC, Uri.parse(mPlayerUrl));
            }
            mMediaPlayer.setMedia(media);
            media.release();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        mMediaPlayer.play();
    }

    private void updateSurfaceFrame() {
        int sw = getWindow().getDecorView().getWidth();
        int sh = getWindow().getDecorView().getHeight();
        LogUtils.w("updateSurfaceFrame sw="+sw+", sh="+sh);

        int displayWidth = sw;
        int displayHeight = (int)(sw * mVideoHeight * 1.0f / mVideoWidth);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(displayWidth, displayHeight);
        mSurfaceView.setLayoutParams(params);
    }

    @Override
    public void onEvent(MediaPlayer.Event event) {

    }

    @Override
    public void onNewVideoLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        LogUtils.w("onNewVideoLayout width="+width+", height="+height
                +", sarNum="+sarNum+", sarDen="+sarDen);
        LogUtils.w("onNewVideoLayout visibleWidth="+visibleWidth+", visibleHeight="+visibleHeight);

        if (mVideoWidth != visibleWidth || mVideoHeight != visibleHeight) {
            mVideoWidth = visibleWidth;
            mVideoHeight = visibleHeight;
            updateSurfaceFrame();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.getVLCVout().detachViews();
    }
}
