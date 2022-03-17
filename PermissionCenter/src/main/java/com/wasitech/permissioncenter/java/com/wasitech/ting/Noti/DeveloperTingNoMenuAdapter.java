package com.wasitech.permissioncenter.java.com.wasitech.ting.Noti;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;

import java.util.List;

import com.wasitech.database.LocalDB;
import de.hdodenhof.circleimageview.CircleImageView;

public abstract class DeveloperTingNoMenuAdapter extends RecyclerView.Adapter<DeveloperTingNoMenuAdapter.ViewHolder>{

    private final List<UserWithTingNo> users;
    private final LocalDB db;
    public DeveloperTingNoMenuAdapter(LocalDB db){
        this.db=db;
        this.users =db.getTingNoUserList();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tingno_ting_back,parent,false);
        return new ViewHolder(view);
    }

    protected abstract boolean onLongClicked(String id);
    protected abstract void onClick(String id);

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserWithTingNo user= this.users.get(position);
        holder.user.setText(user.getName());
        holder.text.setText(user.getTingno().getText());
        int c=db.getDeveloperTingCount(user.getUid());
        holder.time.setText(c+"");
        if(c>0){
            holder.seen.setImageResource(R.drawable.tick_blue);
        }
        if(db.picExist(user.getUid())){
            Bitmap map=Basics.Img.parseBitmap(db.getPic(user.getUid()));
            if(map!=null)
                holder.img.setImageBitmap(map);
        }

        holder.itemView.setOnLongClickListener((v)-> onLongClicked(user.getUid()));
        holder.itemView.setOnClickListener((v)-> onClick(user.getUid()));
    }
    @Override
    public int getItemCount() {
        return users.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView text;
        public TextView user;
        public TextView time;
        public CircleImageView img;
        public ImageView seen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text =itemView.findViewById(R.id.tingno_user);
            time =itemView.findViewById(R.id.tingno_time);
            user=itemView.findViewById(R.id.tingno_user_counter);
            img=itemView.findViewById(R.id.tingno_user_img);
            seen=itemView.findViewById(R.id.seen);
        }
    }
}
