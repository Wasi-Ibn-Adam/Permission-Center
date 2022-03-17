package com.wasitech.permissioncenter.java.com.wasitech.assist.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.wasitech.basics.Storage;
import com.wasitech.basics.classes.Basics;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

import com.wasitech.database.Params;

public class ScreenShooter {

    private static final int VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY | DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;
    public static boolean takeScreenshot(final Context context,final MediaProjection mediaProjection) {
        Toast.makeText(context, "Enter", Toast.LENGTH_SHORT).show();
        if (mediaProjection == null){
            Toast.makeText(context, " media pro null", Toast.LENGTH_SHORT).show();
            return false;
        }
        final int density = context.getResources().getDisplayMetrics().densityDpi;
        final Point size = new Point();

        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(size);
        final int width = size.x, height = size.y;

        // start capture reader
        final  ImageReader imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 2);
        final VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay(Params.SCREENCAP_NAME, width, height, density, VIRTUAL_DISPLAY_FLAGS, imageReader.getSurface(), null, null);

        imageReader.setOnImageAvailableListener(reader -> {
            Basics.toasting(context,"img available");
            Image img = reader.acquireLatestImage();
            ByteBuffer buffer = img.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String path=Basics.Img.parseFile(bytes).getPath();
            Basics.Img.mediaScanner(context,path);
            mediaProjection.stop();
        }, null);

        mediaProjection.registerCallback(new MediaProjection.Callback() {
            @Override
            public void onStop() {
                super.onStop();
                if (virtualDisplay != null)
                    virtualDisplay.release();
                mediaProjection.unregisterCallback(this);
            }
        }, null);
        return true;
    }

    public static String takeScreenshot(View view,Context context) {
        Bitmap bitmap=takeScreenshot(view);
        if(bitmap==null)
            return null;

        File file= Storage.CreateDataFile(Storage.IMG, ".jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)){
                Basics.AudioVideo.Vibrator(context, 50L);
            }
            outputStream.flush();
            outputStream.close();
            return file.getPath();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
    public static Bitmap takeScreenshot(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            //view.buildDrawingCache(true);
            Bitmap bitmap=Bitmap.createBitmap(view.getDrawingCache());
            // Canvas canvas = new Canvas(bitmap);
            //view.draw(canvas);
            view.setDrawingCacheEnabled(false);
            return bitmap;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }




}