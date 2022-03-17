package com.wasitech.permissioncenter.java.com.wasitech.camera.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ortiz.touchview.TouchImageView;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.activities.ImageListAct;
import com.wasitech.camera.classes.Images;
import com.wasitech.theme.Theme;

public class SliderImageAdapter extends RecyclerView.Adapter<SliderImageAdapter.SlideImageView> {

    private final Context mContext;

    public SliderImageAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public SlideImageView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate( R.layout.slide_image_view,parent,false);
        return new SlideImageView(v);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull SlideImageView holder) {
        super.onViewDetachedFromWindow(holder);
        holder.img.resetZoom();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull SlideImageView holder) {
        super.onViewAttachedToWindow(holder);
        holder.img.resetZoom();
    }

    public void nowUpdate(int pos){
        ImageListAct.list.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideImageView holder, int position) {
        try {
            Images img = ImageListAct.list.get(position);
            holder.img.setImageURI(Uri.parse(img.getPath()));
            holder.txt.setText(img.getName());
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public int getItemCount() {
        return ImageListAct.list.size();
    }

    public static class SlideImageView extends RecyclerView.ViewHolder{
        TouchImageView img;
        TextView txt;
        public SlideImageView(@NonNull View v) {
            super(v);
            img=v.findViewById(R.id.touch_img);
            txt=v.findViewById(R.id.img_txt);
            img.setNestedScrollingEnabled(true);
            Theme.textView(txt);
        }
    }

}
