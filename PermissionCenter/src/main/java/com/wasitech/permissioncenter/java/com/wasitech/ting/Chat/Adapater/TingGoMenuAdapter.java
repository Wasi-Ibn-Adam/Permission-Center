package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Adapater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.wasitech.ting.Chat.Activity.TingGoChatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.classes.UserLastTing;

import java.util.ArrayList;

import com.wasitech.database.LocalDB;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("SetTextI18n")
public abstract class TingGoMenuAdapter extends RecyclerView.Adapter<TingGoMenuAdapter.ViewHolder> implements View.OnLongClickListener {
    private final ArrayList<UserLastTing> list;
    private final Activity context;

    public TingGoMenuAdapter(Activity context, ArrayList<UserLastTing> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tinggo_menu, parent, false);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserLastTing user = list.get(position);
        holder.name.setText(user.getName());
        /*
        TingGoBase.USER_PIC_BASE.child(id).getBytes(5*1024*104)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Bitmap map1=Basics.parseBitmap(task.getResult());
                        if(map1!=null)
                            holder.img.setImageBitmap(map1);
                            db.updatePic(id,Basics.parseBytes(map1));
                    }
                });
        */
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

        int n=db.getTingCount(user.getUid());
        if(n>0) {
            holder.counter.setText(n + "");
            holder.counter.setVisibility(View.VISIBLE);
        } else {
            holder.counter.setVisibility(View.INVISIBLE);
        }
                holder.time.setText(user.getTimeText() + "");
        holder.ting.setText(user.getText() + "");
        holder.itemView.setOnClickListener(v -> {
            TingGoChatActivity.setUser(user);
            context.startActivity(new Intent(context, TingGoChatActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CircleImageView img;
        public TextView counter;
        public TextView ting;
        public TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            img = itemView.findViewById(R.id.user_img);
            counter = itemView.findViewById(R.id.msg_counter);
            time = itemView.findViewById(R.id.ting_time);
            ting = itemView.findViewById(R.id.last_ting);
        }
    }

}
