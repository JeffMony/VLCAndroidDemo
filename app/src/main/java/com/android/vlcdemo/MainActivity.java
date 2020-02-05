package com.android.vlcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.videolan.libvlc.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer player = new MediaPlayer(this);

    }
}
