package com.bytedance.videoplayer;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    private ImageButton buttonPlay;
    private VideoView videoView;
//    private SeekBar seekBar;
    private MediaController mediaController;
    private Boolean fullScreen;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fullScreen = (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT);
        tryFullScreen(!fullScreen);
    }
    private void tryFullScreen(boolean fullScreen) {
        if (VideoPlayerActivity.this instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) VideoPlayerActivity.this).getSupportActionBar();
            if (supportActionBar != null) {
                if (fullScreen) {
                    supportActionBar.hide();
                } else {
                    supportActionBar.show();
                }
            }
        }
        setFullScreen(fullScreen);
    }


    private void setFullScreen(boolean fullScreen) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        if (fullScreen) {
            attrs.flags = attrs.flags | WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            attrs.flags = attrs.flags & (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        setTitle("VideoView");

//        seekBar = findViewById(R.id.seekBar);
        videoView = findViewById(R.id.videoView);
        buttonPlay = findViewById(R.id.imageButton);
        mediaController = new MediaController(this);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                else{
                videoView.start();
                buttonPlay.setVisibility(View.INVISIBLE);
                }
            }
        });
//        videoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(videoView.isPlaying()){
//                    videoView.pause();
//                    buttonPlay.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.bytedance));
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        mediaController.show();
        //seekBar.setMax(videoView.getDuration()/1000);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                videoView.seekTo(progress*1000);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

    }
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
