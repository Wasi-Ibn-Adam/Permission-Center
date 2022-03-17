package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Adapater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.wasitech.assist.R;
import com.wasitech.ting.Chat.Module.Ting;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.classes.Tinggo;

import java.util.ArrayList;

import com.wasitech.database.LocalDB;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ClickableViewAccessibility")
public abstract class TingGoChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnLongClickListener {
    private final ArrayList<Ting> list;
    private final TingUser user;
    private final Context context;
    private Bitmap map;
    int[] reactions = new int[]{R.drawable.react_hurt, R.drawable.react_yey, R.drawable.react_wow,
            R.drawable.react_angry, R.drawable.react_love, R.drawable.react_ooh, R.drawable.react_yum, R.drawable.react_love_anger};

    public TingGoChatAdapter(Context context, ArrayList<Ting> list, TingUser user) {
        this.list = list;
        this.context = context;
        this.user = user;
        byte[] b = new LocalDB(context).getPic(user.getUid());
        map = Basics.Img.parseBitmap(b);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_detail_recycler, parent, false);
        } else if (viewType == Tinggo.OTHER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tinggo_reciever, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tinggo_sender, parent, false);
        }
        return new ReceiverHolder(view);
    }

    private void setHeader(View v) {
        CircleImageView img = v.findViewById(R.id.user_img);
        EditText name = v.findViewById(R.id.name);
        EditText phone = v.findViewById(R.id.phone_num);
        EditText email = v.findViewById(R.id.email);

        Glide.with(context).asBitmap().load(map).placeholder(R.drawable.img_user).into(img);
        String n = "", e = "", p = "";
        if (user.getName() != null)
            n = user.getName();
        if (user.getEmail() != null)
            e = user.getEmail();
        if (user.getNumber() != null)
            p = user.getNumber();
        name.setText(n);
        email.setText(e);
        phone.setText(p);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 2;
        return getTing(position).getTinggo().getWho();
    }

    private Ting getTing(int p) {
        return list.get(p - 1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        if (position == 0) {
            setHeader(holder1.itemView);
            return;
        }

        ReceiverHolder holder = (ReceiverHolder) holder1;
        Ting ting = getTing(position);
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();
        ReactionPopup popup = new ReactionPopup(context, config, (p) -> {
            if (p >= 0) {
                if (p == ting.getReaction() - 1) {
                    holder.react.setVisibility(View.INVISIBLE);
                    onReaction(ting.getTinggo().getUid(), ting.getMsgId(), 0);
                } else {
                    holder.react.setVisibility(View.VISIBLE);
                    holder.react.setImageResource(reactions[p]);
                    onReaction(ting.getTinggo().getUid(), ting.getMsgId(), p + 1);
                }
                notifyDataSetChanged();
            }
            return true; // true is closing popup, false is requesting a new selection
        });
        if (ting.getReaction() > 0) {
            holder.react.setImageResource(reactions[ting.getReaction() - 1]);
            holder.react.setVisibility(View.VISIBLE);
        } else {
            holder.react.setVisibility(View.INVISIBLE);
        }

        holder.text.setText(ting.getTinggo().getText());
        holder.time.setText(Tinggo.onlyTime(ting.getTinggo().getTime()));

        if (ting.getTinggo().getWho() == Tinggo.OTHER) {
            holder.text.setOnTouchListener((v, event) -> {
                popup.onTouch(v, event);
                return false;
            });
            holder.react.setOnTouchListener((v, event) -> {
                popup.onTouch(v, event);
                return false;
            });
        }
    }

    protected abstract void onReaction(String uid, String msgId, int i);


    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public static class ReceiverHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView time;
        public ImageView react;

        //public CircleImageView pic;
        public ReceiverHolder(@NonNull View itemView) {
            super(itemView);
            react = itemView.findViewById(R.id.ting_react);
            time = itemView.findViewById(R.id.ting_time);
            text = itemView.findViewById(R.id.ting_text);
            // pic = itemView.findViewById(R.id.ting_img);
        }
    }
}