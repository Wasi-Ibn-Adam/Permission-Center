package com.wasitech.permissioncenter.java.com.wasitech.camera.pop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.wasitech.assist.R;
import com.wasitech.assist.popups.ThemePopUp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.Params;

import java.io.File;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class PicPopUp extends ThemePopUp {
    public PicPopUp(Context activity, byte[] bytes) {
        super(activity, R.layout.pop_up_pic_view_l, R.layout.pop_up_pic_view_d);
        showAtLocation(view, Gravity.CENTER, 1, 1);
        CircleImageView img = view.findViewById(R.id.img_view);
        Glide.with(activity).load(bytes).into(img);
        view.findViewById(R.id.img_close).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.img_edit).setOnClickListener(v -> editImg());
        view.findViewById(R.id.img_share).setOnClickListener(v -> shareImg(activity, bytes));
    }

    public PicPopUp(Context activity, byte[] bytes, boolean edit, boolean share) {
        super(activity, R.layout.pop_up_pic_view_l, R.layout.pop_up_pic_view_d);
        showAtLocation(view, Gravity.CENTER, 1, 1);
        CircleImageView img = view.findViewById(R.id.img_view);
        Glide.with(activity).load(bytes).into(img);

        if (!edit) {
            view.findViewById(R.id.img_edit).setVisibility(View.GONE);
        }
        if (!share) {
            view.findViewById(R.id.img_share).setVisibility(View.GONE);
        }

        if (edit || share) {
            view.findViewById(R.id.img_close).setOnClickListener(v -> dismiss());
            view.findViewById(R.id.img_edit).setOnClickListener(v -> editImg());
            view.findViewById(R.id.img_share).setOnClickListener(v -> shareImg(activity, bytes));
        } else {
            view.findViewById(R.id.img_edit).setVisibility(View.GONE);
            view.findViewById(R.id.img_share).setVisibility(View.GONE);
            view.findViewById(R.id.img_close).setVisibility(View.GONE);
        }
    }

    public PicPopUp(Context activity, byte[] bytes, boolean extra) {
        super(activity, R.layout.pop_up_pic_view_l, R.layout.pop_up_pic_view_d);
        showAtLocation(view, Gravity.CENTER, 1, 1);
        CircleImageView img = view.findViewById(R.id.img_view);
        Glide.with(activity).load(bytes).into(img);
        if (!extra) {
            view.findViewById(R.id.img_close).setVisibility(View.GONE);
            view.findViewById(R.id.img_edit).setVisibility(View.GONE);
            view.findViewById(R.id.img_share).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.img_close).setOnClickListener(v -> dismiss());
            view.findViewById(R.id.img_edit).setOnClickListener(v -> editImg());
            view.findViewById(R.id.img_share).setOnClickListener(v -> shareImg(activity, bytes));
        }
    }

    public abstract void editImg();

    private void shareImg(Context c, byte[] b) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            File temp = new File(c.getObbDir(), "/Assist DI.jpg");
            FileOutputStream fin = new FileOutputStream(temp);
            fin.write(b);
            fin.close();
            Uri uri = FileProvider.getUriForFile(c.getApplicationContext(), "com.wasitech.assist.FileProvider", temp);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(Intent.createChooser(sharingIntent, Params.SHARE_VIA));
        } catch (Exception e) {
            Basics.toasting(c, "An Error Occurred, Try Again Later");
            Issue.print(e, PicPopUp.class.getName());
        }
    }
}
