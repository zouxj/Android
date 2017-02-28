package com.example.dell.musicplayer;

import android.app.Activity;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout frameLayout = (RelativeLayout) findViewById(R.id.lv_framgnet);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        InputStream inputStream= getResources().openRawResource(R.raw.fanren);
        AssetManager am = getAssets();
        MusicView musicView = null;
        try {
            musicView = new MusicView(this,am.openFd("fanren.mp3").getFileDescriptor(),inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameLayout.addView(musicView.getView(),layoutParams);
    }
}
