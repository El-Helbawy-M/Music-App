package com.example.musicapp.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicapp.Activites.MainActivity;
import com.example.musicapp.Adapters.SongsAdabter;
import com.example.musicapp.Models.Song;
import com.example.musicapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HomeScreen extends Fragment {
    private static final int REQUEST_PERMISSION = 99 , OPEN_DOCUMENT_REQUEST = 1001;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public void click(View view){

    }
    public HomeScreen() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        ArrayList<Song> songs = new ArrayList<Song>();
        MainActivity mainActivity = (MainActivity) getActivity();
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
        }
        else{
            ContentResolver contentResolver = getActivity().getContentResolver();
            Uri songPath = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = contentResolver.query(songPath,null, MediaStore.Audio.Media.IS_MUSIC+"!=0", null,null);
            String path = "";
            if(cursor != null && cursor.getCount() != 0 ){
                while(cursor.moveToNext()) {
                    if(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)).contains(".mp3"))
                    {
                        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                        String direction = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                        String size = ""+df2.format(Double.parseDouble(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))) / 1000000)+" MP";
                        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                        Song song = new Song(title,direction,artist,size , duration);
                        songs.add(song);
                    }
                }
            }
            cursor.close();
            RecyclerView recyclerView = view.findViewById(R.id.items);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new SongsAdabter(songs,getContext()));
        }

        return view;
    }
}