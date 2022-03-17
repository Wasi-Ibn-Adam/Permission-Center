package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Adapater;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.classes.TingUser;

import java.util.ArrayList;

import com.wasitech.database.LocalDB;
import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TingGoUserListAdapter extends RecyclerView.Adapter<TingGoUserListAdapter.ViewHolder> implements View.OnClickListener {
    private final ArrayList<TingUser> list;
    private final Context context;

    public TingGoUserListAdapter(Context context, ArrayList<TingUser> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tinggo_user, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TingUser user = list.get(position);
        holder.name.setText(user.getName());
        LocalDB db = new LocalDB(context);
        byte[]pic=db.getPic(user.getUid());
        if(pic!=null)
            Glide.with(context).asBitmap().placeholder(R.drawable.loading).load(pic).into(holder.img);
        else if(user.getImagePath()!=null){
            Glide.with(context).asBitmap().placeholder(R.drawable.loading).addListener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    db.updatePic(user.getUid(), Basics.Img.parseBytes(resource));
                    return false;
                }
            }).load(user.getImagePath()).into(holder.img);
        }
        else{
            Glide.with(context).asBitmap().load(R.drawable.img_user).into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CircleImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tinggo_user);
            img = itemView.findViewById(R.id.tinggo_user_img);
        }
    }

}
