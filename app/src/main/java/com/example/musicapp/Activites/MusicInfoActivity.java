package com.example.musicapp.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.example.musicapp.Models.Song;
import com.example.musicapp.R;
public class MusicInfoActivity extends AppCompatActivity {
    Intent intent;
    TextView name , runTime , maxTime;
    ImageButton play;
    MediaPlayer player ;
    SeekBar seekBar;
    private Handler handler;
    private Runnable listner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);
        intent = getIntent();
        final Song song = (Song) intent.getSerializableExtra("Song");
        player = MediaPlayer.create(MusicInfoActivity.this,Uri.parse(song.getSongPath()));
        set_values(song);
        seekBar.setMax(song.getDuration());
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDate();
                if(player.isPlaying()){
                    player.pause();
                    play.setImageResource(R.drawable.play_foreground);
                }
                else {
                    player.start();
                    handler.postDelayed(listner, 0);
                    play.setImageResource(R.drawable.pause_foreground);

                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if(isFromUser){
                    player.seekTo(progress);
                    seekBar.setProgress(progress);
                }
                runTime.setText(calculateTime(progress));
                if(progress == seekBar.getMax()){
                    play.setImageResource(R.drawable.play_foreground);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    void set_values(Song song){
        name = (TextView) findViewById(R.id.Name);
        name.setText(song.getTitle());
        runTime = (TextView) findViewById(R.id.runTime);
        maxTime = (TextView) findViewById(R.id.maxTime);
        seekBar = (SeekBar) findViewById(R.id.progress_bar);
        maxTime.setText(""+calculateTime(song.getDuration()));
        play = (ImageButton) findViewById(R.id.play);
    }
    String calculateTime(int duration){
        duration/=1000;
        int minutes = duration / 60 ;
        int seconds = duration - (minutes * 60);
        return (seconds < 10) ? minutes+":0"+seconds : minutes+":"+seconds;
    }
    void upDate(){
        handler = new Handler();
        listner = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(player.getCurrentPosition());
                handler.postDelayed(this,50);
            }
        };
    }
}