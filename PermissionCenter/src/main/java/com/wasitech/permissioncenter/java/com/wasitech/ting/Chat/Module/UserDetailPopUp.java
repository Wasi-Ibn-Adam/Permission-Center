package com.wasitech.permissioncenter.java.com.wasitech.ting.Chat.Module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wasitech.assist.R;
import com.wasitech.basics.classes.Basics;
import com.wasitech.assist.classes.TingUser;
import com.wasitech.assist.popups.ThemePopUp;

import com.wasitech.database.LocalDB;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailPopUp extends ThemePopUp {

    @SuppressLint("SetTextI18n")
    public UserDetailPopUp(Context context, TingUser user) {
        super(context, R.layout.activity_user_detail_page);
        CircleImageView img = view.findViewById(R.id.user_img);
        EditText name = view.findViewById(R.id.name);
        EditText email = view.findViewById(R.id.email);
        EditText number = view.findViewById(R.id.phone_num);
        FloatingActionButton fab = view.findViewById(R.id.close);

        String n = "", e = "", p = "";
        if (user.getName() != null)
            n = user.getName();
        if (user.getEmail() != null)
            e = user.getEmail();
        if (user.getNumber() != null)
            p = user.getNumber();

        fab.setOnClickListener(v -> dismiss());
        name.setText(n);
        email.setText(e);
        number.setText(p);
        LocalDB db = new LocalDB(context.getApplicationContext());
        byte[] pic = db.getPic(user.getUid());
        if (pic != null)
            Glide.with(context).asBitmap().placeholder(R.drawable.loading).load(pic).into(img);
        else if (user.getImagePath() != null) {
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
            }).load(user.getImagePath()).into(img);
        } else {
            Glide.with(context).asBitmap().load(R.drawable.img_user).into(img);
        }
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}