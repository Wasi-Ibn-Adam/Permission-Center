package com.wasitech.permissioncenter.java.com.wasitech.camera.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.wasitech.assist.R;
import com.wasitech.assist.popups.ThemedPopUps;
import com.wasitech.basics.Storage;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.adapter.SliderImageAdapter;
import com.wasitech.camera.classes.Images;
import com.wasitech.theme.Theme;

public class ImageViewerAct extends AssistCompatActivity {
    private ViewPager2 mViewPager;
    private SliderImageAdapter adapter;


    public ImageViewerAct() {
        super(R.layout.act_img_viewer);
    }

    private String getFilePath() {
        int m = mViewPager.getCurrentItem();
        return ImageListAct.list.get(m).getPath();
    }

    private Images getImage() {
        int m = mViewPager.getCurrentItem();
        return (ImageListAct.list.get(m));
    }

    @Override
    public void setViews() {
        try {
            mViewPager = findViewById(R.id.viewPage);
            setBack();
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setTheme() {
        try {
            Theme.Activity(this);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
        try {
            int p = getIntent().getIntExtra("pos", 0);
            mViewPager.setAnimation(new AlphaAnimation(0.0f, 1.0f));
            adapter = new SliderImageAdapter(this);
            mViewPager.setNestedScrollingEnabled(true);
            mViewPager.setAdapter(adapter);
            mViewPager.setCurrentItem(p, false);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions() {
        try {
            Toolbar bar = findViewById(R.id.toolbar);
            bar.inflateMenu(R.menu.menu_image_action);
            bar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtras() {
    }

    @Override
    public void setPermission() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            if (item.getItemId() == R.id.info_btn) {
                Images img = getImage();
                String b = "Name: " + img.getName() +
                        "\nLast Modified: " + img.getDate() +
                        "\nPath: " + img.getPath();
                ThemedPopUps.BackgroundPopup(ImageViewerAct.this, b);

            } else if (item.getItemId() == R.id.share_btn) {
                Basics.Send.share(ImageViewerAct.this, getFilePath());
            } else if (item.getItemId() == R.id.delete_btn) {
                try {
                    String path = getFilePath();
                    adapter.nowUpdate(mViewPager.getCurrentItem());
                    MediaScannerConnection.scanFile(getApplicationContext(),
                            new String[]{path}, null,
                            (p1, uri) -> {
                                if (getContentResolver().delete(uri, null, null) <= 0) {
                                    Basics.toasting(getApplicationContext(), "Unable to Delete Image!");
                                    ImageListAct.list = Storage.getImgList();
                                }
                            });
                    if (ImageListAct.list.size() == 0)
                        finish();

                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            }
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        return super.onOptionsItemSelected(item);
    }

    public static class Intents {
        public static Intent showPic(Context context, int pos) {
            return new Intent(context, ImageViewerAct.class)
                    .putExtra("pos", pos)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

}
