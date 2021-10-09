package com.example.musicapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicapp.Activites.MusicInfoActivity;
import com.example.musicapp.Models.Song;
import com.example.musicapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class SongsAdabter extends RecyclerView.Adapter<SongsAdabter.SongViewHolder> {
    ArrayList<Song> songs;
    Context context;
    public SongsAdabter(ArrayList<Song> songs, Context context ){
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_field,null,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        final Song song = songs.get(position);
        holder.name.setText(song.getTitle());
        holder.size.setText(song.getSize());
        holder.artist.setText(song.getArtist());
        holder.field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , MusicInfoActivity.class);
                intent.putExtra("Song",(Serializable) song);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder{
        public TextView name , size , artist;
        public LinearLayout field;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.NameView);
            size = itemView.findViewById(R.id.SizeView);
            artist = itemView.findViewById(R.id.ArtistView);
            field = itemView.findViewById(R.id.SongField);
        }
    }
}
