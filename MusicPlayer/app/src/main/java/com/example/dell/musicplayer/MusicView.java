package com.example.dell.musicplayer;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * Created by dell on 2017/1/6.
 */

public class MusicView {
    private View view;
    private FileDescriptor fileDescriptor;
    private InputStream inputStream;
    private LyricView lvView;
    private Player player;
    private  SeekBar musicProgress;
    private Handler handler = new Handler();
    /**
     *
     */
    private Context context;
    public  MusicView(Context context,FileDescriptor fileDescriptor,InputStream inputStream){
        this.context =context;
        this.fileDescriptor = fileDescriptor;
        this.inputStream = inputStream;
        view = inflate(R.layout.player_view);
        initView(view);
        initData();
    }
    public  View inflate(int resId){
        return LayoutInflater.from(context).inflate(resId,null);
    }
    /**
     * 初始化View
     */
    public void initView(View view){
        lvView = (LyricView) view.findViewById(R.id.lvView);
        musicProgress = (SeekBar)view. findViewById(R.id.music_progress);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ly_layout);
        view.findViewById(R.id.tv_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                linearLayout.setVisibility(View.GONE);

            }
        });
    }
    /**
     * 返回一个View
     */
    public View getView(){
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData(){
        player = new Player(musicProgress);
        player.playUrl(fileDescriptor);
        lvView.initLyricFile(inputStream);
        if (!player.mediaPlayer.isPlaying()) {
            handler.post(runnable);
        } else {
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * 处理一些业务逻辑
     */
    public void doSomeThing(){

    }
    private void initMediaPlayer() {

    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (player.mediaPlayer.isPlaying()) {
                long time = player.mediaPlayer.getCurrentPosition();
                lvView.updateLyrics((int) time, player.mediaPlayer.getDuration());
            }
            handler.postDelayed(this, 100);
        }
    };
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        if (player != null) {
            player.stop();
            player = null;
        }
    }
}

