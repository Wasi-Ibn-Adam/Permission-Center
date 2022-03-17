package com.wasitech.permissioncenter.java.com.wasitech.camera.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.camera.classes.Images;
import com.wasitech.theme.Theme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private final ArrayList<Images> list;

    public ImagesAdapter(List<Images> imgList) {
        listSize(imgList.size());
        list = (ArrayList<Images>) imgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pic_view, parent, false);
        return new ViewHolder(setTheme(parent.getContext(), view));
    }

    private View setTheme(Context context, View v) {
        if (Theme.getCur() == Theme.DARK_THEME)
            v.findViewById(R.id.row_bg).setBackground(ContextCompat.getDrawable(context, R.drawable.shape_row_1_light));
        return v;
    }

    protected abstract boolean OnLongClickListener(Images img,int i);

    protected abstract void listSize(int size);
    protected abstract void onClick(int i);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Images img = list.get(position);
        holder.title.setText(img.getName());
        Glide.with(holder.itemView).
                load(Uri.fromFile(new File(img.getPath())))
                .placeholder(R.drawable.cam_outline)
                .override(100)
                .centerCrop()
                .into(holder.img);
        holder.itemView.setOnClickListener(v -> onClick(position));
        holder.itemView.setOnLongClickListener(v -> OnLongClickListener(img,position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
        }

    }


}
