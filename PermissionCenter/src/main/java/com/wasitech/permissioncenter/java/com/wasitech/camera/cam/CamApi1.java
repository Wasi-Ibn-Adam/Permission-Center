package com.wasitech.permissioncenter.java.com.wasitech.camera.cam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.wasitech.assist.command.family.Intents;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.database.CloudDB;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionChecker;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Objects;

import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

@SuppressWarnings("deprecation")
public class CamApi1 {
    private static final SparseIntArray ORIENTATION = new SparseIntArray();
    static {
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);
    }
    private final boolean update;
    private CaptureRequest.Builder builder;
    private final int id;
    private final Context context;
    private final CameraManager manager;
    private int width;
    private int height;

    private final CameraCaptureSession.CaptureCallback callback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }

        @Override
        public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, int sequenceId, long frameNumber) {
            super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);
        }

        @Override
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession session, int sequenceId) {
            super.onCaptureSequenceAborted(session, sequenceId);
        }

        @Override
        public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
            super.onCaptureBufferLost(session, request, target, frameNumber);
        }
    };

    public CamApi1(Context context, int cam, boolean update) {
        this.context = context;
        this.id = cam;
        this.update = update;
        height = 480;
        width = 640;
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (Permission.Check.camera(context)) {
            if (!Cam.allowCamera2Support(context, id)) {
                oldCamPic();
            } else {
                newCamPic();
            }
        }
        else {
            if (update) {
                if (id == PermissionChecker.FRONT_CAM)
                    context.startActivity(Intents.picPermission(context, PermissionChecker.FRONT_CAM));
                else
                    context.startActivity(Intents.picPermission(context, PermissionChecker.RARE_CAM));
            }
        }
    }

    private void oldCamPic() {
        try {
            final Camera cam = Camera.open(id);
            final Camera.Parameters parameters = cam.getParameters();
            parameters.setRotation(getRotation(context.getResources().getConfiguration().orientation));
            parameters.setPictureFormat(ImageFormat.JPEG);
            cam.setParameters(parameters);
            try {
                cam.setPreviewDisplay(null);
                cam.startPreview();
                cam.takePicture(null, null, (bytes, camera) -> {
                    if (update) {
                        File photo = Basics.Img.parseFile(bytes);
                        Basics.Img.mediaScanner(context, photo.getPath());
                    } else {
                        CloudDB.PicCenter.ByteSend(bytes);
                    }
                    cam.stopPreview();
                    cam.release();
                });
            }
            catch (IOException e) {
                if (update)
                    Toast.makeText(context, "Error: com.wasitech.assist.Cam.102", Toast.LENGTH_SHORT).show();
                cam.stopPreview();
                cam.release();
                Issue.print(e, getClass().getName());
            }
        } catch (Exception e) {
            if (update) {
                context.startActivity(CamApi2.Intents.takePic(context,id));
            }
            Issue.print(e,getClass().getName());
        }
    }

    @SuppressLint("MissingPermission")
    private void newCamPic() {
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(manager.getCameraIdList()[id]);
            Size[] size = Objects.requireNonNull(characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(ImageFormat.JPEG);
            width = size[0].getWidth();
            height = size[0].getHeight();
            if (!Permission.Check.camera(context)){
                return;
            }
            manager.openCamera(manager.getCameraIdList()[id], new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    takePic(camera);
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    camera.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    camera.close();
                }
            }, new Handler(Looper.getMainLooper()));
        } catch (CameraAccessException e) {
            if (update)
                Toast.makeText(context, "Error: com.wasitech.assist.Cam.201", Toast.LENGTH_SHORT).show();
            Issue.print(e, getClass().getName());
        }
    }

    @SuppressLint("InlinedApi")
    public void takePic(final CameraDevice camDevice) {
        try {
            builder = camDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            builder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_CAPTURE_INTENT_STILL_CAPTURE);
            ImageReader imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 2);
            imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                protected Image img;

                @Override
                public void onImageAvailable(ImageReader reader) {
                    img = reader.acquireLatestImage();
                    ByteBuffer buffer = img.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    if (update) {
                        String path = Basics.Img.parseFile(bytes).getPath();
                        Basics.Img.mediaScanner(context, path);
                    } else
                        CloudDB.PicCenter.ByteSend(bytes);
                    camDevice.close();
                }
            },
                    new Handler(Looper.getMainLooper()));
            builder.addTarget(imageReader.getSurface());
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(id, info);
            builder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATION.get(info.orientation));
            camDevice.createCaptureSession(Collections.singletonList(imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    try {
                        cameraCaptureSession.capture(builder.build(), callback, new Handler(Looper.getMainLooper()));
                    } catch (CameraAccessException e) {
                        if (update)
                            Toast.makeText(context, "Error: com.wasitech.assist.Cam.204", Toast.LENGTH_SHORT).show();
                        Issue.print(e, CamApi1.class.getName());
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    camDevice.close();
                    if (update)
                        Toast.makeText(context, "Error: com.wasitech.assist.Cam.203", Toast.LENGTH_SHORT).show();
                }
            }, new Handler(Looper.getMainLooper()));

        } catch (CameraAccessException e) {
            if (update)
                Toast.makeText(context, "Error: com.wasitech.assist.Cam.202", Toast.LENGTH_SHORT).show();
            Issue.print(e, CamApi1.class.getName());
        }
    }


    public int getRotation(int orientation) {
        if (orientation == ORIENTATION_UNKNOWN) return 0;
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(id, info);
        orientation = (orientation + 45) / 90 * 90;
        int rotation = 0;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {  // back-facing camera
            rotation = (info.orientation + orientation) % 360;
        }
        return (rotation);
    }
}
