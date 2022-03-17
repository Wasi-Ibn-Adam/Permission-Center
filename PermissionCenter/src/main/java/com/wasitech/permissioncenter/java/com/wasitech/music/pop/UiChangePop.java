package com.wasitech.permissioncenter.java.com.wasitech.music.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;

import static com.wasitech.music.activity.MusicPlayer.MUSIC_PLAYER;

public abstract class UiChangePop extends PopupWindow {
    private int selected;
    private Button change;

    public UiChangePop(Context context) {
        super(context);
        @SuppressLint("InflateParams") View view =
                ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.pop_up_music_ui, null);
        setContentView(view);
        setButtons(view);
        setRecyclerView(view);
        setOutsideTouchable(false);
        setFocusable(false);

        int w=context.getResources().getDisplayMetrics().widthPixels;
        int h=context.getResources().getDisplayMetrics().heightPixels;
        setWidth(w);
        setHeight((int)(h*0.8));
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void setButtons(View view) {
        Button cancel = view.findViewById(R.id.cancel_btn);
        change = view.findViewById(R.id.ui_btn);
        cancel.setOnClickListener(v -> this.dismiss());
        change.setEnabled(false);
        change.setTextColor(Color.GRAY);
        change.setOnClickListener(v -> {
            changeUi();
            dismiss();
            updateNow();
        });
    }

    protected abstract void updateNow();

    private void changeUi() {
        switch (selected) {
            default:
            case 0: {
                ProcessApp.getPref().edit().putInt(MUSIC_PLAYER, 0).apply();
                break;
            }
            case 1: {
                ProcessApp.getPref().edit().putInt(MUSIC_PLAYER,1).apply();
                break;
            }
            case 2: {
                ProcessApp.getPref().edit().putInt(MUSIC_PLAYER, 2).apply();
                break;
            }
        }
    }

    private void setRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.img_recycler);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ImageAdapter() {
            @Override
            protected void onClick(int n, boolean dif) {
                selected = n;
                change.setEnabled(dif);
                change.setTextColor(dif?Color.BLACK:Color.GRAY);
            }
        });
        recyclerView.scrollToPosition(0);
    }
    public static int curClickUi=-1;
    private abstract static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

        @NonNull
        @Override
        public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.row_music_pic_ui,parent,false);
            return new ImageHolder(view);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
            switch (position) {
                default:
                case 0: {
                    holder.img.setImageResource(R.drawable.blue_light);
                    break;
                }
                case 1: {
                    holder.img.setImageResource(R.drawable.black_light);
                    break;
                }
                case 2: {
                    holder.img.setImageResource(R.drawable.pink_light);
                    break;
                }
            }
            int n=ProcessApp.getPref().getInt(MUSIC_PLAYER, 0);
            if (n == position)
                holder.check.setImageTintList(ColorStateList.valueOf(Color.GREEN));
            else if(UiChangePop.curClickUi==-1||UiChangePop.curClickUi!=position)
                holder.check.setImageTintList(ColorStateList.valueOf(Color.DKGRAY));
            else
                holder.check.setImageTintList(ColorStateList.valueOf(Color.LTGRAY));

            holder.itemView.setOnClickListener(v -> {
                boolean t = n != position;
                UiChangePop.curClickUi=position;
                onClick(position, t);
                notifyDataSetChanged();
            });
        }

        protected abstract void onClick(int n, boolean dif);

        @Override
        public int getItemCount() {
            return 3;
        }

        public static class ImageHolder extends RecyclerView.ViewHolder {
            public ImageView check, img;

            public ImageHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img);
                check = itemView.findViewById(R.id.check);
            }
        }
    }

}
