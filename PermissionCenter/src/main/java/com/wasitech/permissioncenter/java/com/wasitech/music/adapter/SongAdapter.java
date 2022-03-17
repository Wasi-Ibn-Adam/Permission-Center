package com.wasitech.permissioncenter.java.com.wasitech.music.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Format;
import com.wasitech.basics.classes.Issue;
import com.wasitech.music.classes.Song;

import java.io.File;
import java.util.ArrayList;

public abstract class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> implements View.OnClickListener, Filterable {
    public ArrayList<Song> songs;
    private final SongFilter filter;

    public SongAdapter(ArrayList<Song> songs) {
        this.songs = songs;
        listSize(songs.size());
        filter=new SongFilter(this, songs);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int lay = R.layout.row_audio;
        if (songs != null && songs.get(0).isVideo()) {
            lay = R.layout.row_video;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(lay, parent, false);
        return new ViewHolder(view);
    }

    protected abstract boolean OnLongClickListener(Song s, int p);

    protected abstract void listSize(int size);

   // private static Song s;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Song song = songs.get(position);
            holder.itemView.setOnClickListener(v -> {
                SongAdapter.this.onClick(v);
                //try {
                //    new Handler(Looper.getMainLooper()).postDelayed(this::notifyDataSetChanged, 400L);
                //} catch (Exception e) {
                //    Issue.print(e, getClass().getName());
                //}
            });
            holder.itemView.setOnLongClickListener(v -> OnLongClickListener(song, position));
            holder.title.setText(song.getTitle());
            holder.time.setText(Format.millisToTime(song.getDuration()));

            if (song.isVideo()) {
                Glide.with(holder.itemView)
                        .load(Uri.fromFile(new File(song.getPath())))
                        .override(250)
                        .placeholder(R.drawable.video)
                        .centerCrop()
                        .into(holder.img);
            }
            //else if (SongService.player != null && SongService.player.isPlaying()) {
            //    if (s != null) {
            //        if (s.areExactlySame(SongService.currSong())) {
            //            Glide.with(holder.itemView.getContext()).asGif().load(R.drawable.transgif).into(holder.img);
            //        }
            //    } else {
            //        if (song.areExactlySame(SongService.currSong())) {
            //            Glide.with(holder.itemView.getContext()).asGif().load(R.drawable.transgif).into(holder.img);
            //        }
            //    }
//
            //}
            //SongAdapter.s = song;
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView time;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            img = itemView.findViewById(R.id.img);
        }

    }

    protected static class SongFilter extends Filter {
        private final SongAdapter adapter;
        private final ArrayList<Song> list;

        public SongFilter(SongAdapter adapter, ArrayList<Song> list) {
            this.adapter = adapter;
            this.list = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Song> model = new ArrayList<>();
                for (Song s:list) if (s.contains(constraint.toString())) model.add(s);
                results.count = model.size();
                results.values = model;
            } else {
                results.count = list.size();
                results.values = list;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.songs = (ArrayList<Song>) results.values;
            adapter.notifyDataSetChanged();
            adapter.listSize(results.count);
        }
    }
}
