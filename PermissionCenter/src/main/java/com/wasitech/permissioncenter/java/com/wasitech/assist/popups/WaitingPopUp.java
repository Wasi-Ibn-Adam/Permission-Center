package com.wasitech.permissioncenter.java.com.wasitech.assist.popups;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.basics.app.ProcessApp;

import com.wasitech.basics.classes.Issue;
import de.hdodenhof.circleimageview.CircleImageView;

public abstract class WaitingPopUp extends ThemePopUp {

    public WaitingPopUp(Context context) {
        super(context, R.layout.activity_loading);
        Glide.with(context).asGif().load(gif()).into((ImageView) view.findViewById(R.id.videoView));
        Glide.with(context).load(new ColorDrawable(Color.LTGRAY)).into((CircleImageView) view.findViewById(R.id.loading_bg_color));
        setOnDismissListener(this::onClose);
        setFocusable(false);
        new Handler(context.getMainLooper()).post(this::runner);
        setView();
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }
    public WaitingPopUp(Context context,String text) {
        super(context, R.layout.activity_loading);
        Glide.with(context).asGif().load(gif()).into((ImageView) view.findViewById(R.id.videoView));
        Glide.with(context).load(new ColorDrawable(Color.LTGRAY)).into((CircleImageView) view.findViewById(R.id.loading_bg_color));
        ((TextView)view.findViewById(R.id.loading_txt)).setText(text);
        setOnDismissListener(this::onClose);
        setFocusable(false);
        new Handler(context.getMainLooper()).post(this::runner);
        setView();
        try {
            showAtLocation(view, Gravity.CENTER, 0, 0);
        }
        catch (Exception e){
            Issue.print(e, WaitingPopUp.class.getName());
        }
    }
    public WaitingPopUp(Context context,String text,boolean show) {
        super(context, R.layout.activity_loading);
        Glide.with(context).asGif().load(gif()).into((ImageView) view.findViewById(R.id.videoView));
        Glide.with(context).load(new ColorDrawable(Color.LTGRAY)).into((CircleImageView) view.findViewById(R.id.loading_bg_color));
        ((TextView)view.findViewById(R.id.loading_txt)).setText(text);
        setOnDismissListener(this::onClose);
        setFocusable(false);
        new Handler(context.getMainLooper()).post(this::runner);
        setView();
        if(show){
            try {
                showAtLocation(view, Gravity.CENTER, 0, 0);
            }
            catch (Exception e){
                Issue.print(e, WaitingPopUp.class.getName());
            }
        }
    }

    public void show(){
        try {
            showAtLocation(view, Gravity.CENTER, 0, 0);
        }
        catch (Exception e){
            Issue.print(e, WaitingPopUp.class.getName());
        }
    }
    public int GIF() {
        return 4;
    }

    private int gif() {
        switch (GIF()) {
            default: case 0: {
                return R.drawable.loading;
            }
            case 1:case 4: {
                return R.drawable.loading_3;
            }
        }
    }

    private void setView() {
        try {
            if (GIF() == 4||GIF()==1) {
                if (ProcessApp.isDarkTheme())
                    ((TextView) view.findViewById(R.id.loading_txt)).setTextColor(ColorStateList.valueOf(Color.WHITE));
                else
                    ((TextView) view.findViewById(R.id.loading_txt)).setTextColor(ColorStateList.valueOf(Color.BLACK));
            }
            else{
                view.findViewById(R.id.loading_txt).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.loading_bg_color).setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Issue.print(e, WaitingPopUp.class.getName());
        }

    }

    protected abstract void runner();

    public abstract void onClose();

}
