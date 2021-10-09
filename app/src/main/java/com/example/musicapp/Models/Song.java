package com.example.musicapp.Models;

import android.media.MediaPlayer;

import java.io.Serializable;

public class Song implements Serializable {
    private String title , songPath , artist ,  size ;
    int  duration;
    public Song(String title, String songPath , String artist , String size , int duration ){
        this.title = title;
        this.songPath = songPath;
        this.artist = artist;
        this.size = size;
        this.duration = duration;
    }
    public String getSize(){return this.size;}
    public String getSongPath(){
        return this.songPath;
    }
    public String getArtist(){return this.artist;}
    public String getTitle() {
        return this.title;
    }
    public int getDuration(){return  this.duration;}
}
