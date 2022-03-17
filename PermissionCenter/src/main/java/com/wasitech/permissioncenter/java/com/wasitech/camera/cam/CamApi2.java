package com.wasitech.permissioncenter.java.com.wasitech.camera.cam;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.wasitech.assist.R;
import com.wasitech.basics.activityies.AssistCompatActivity;
import com.wasitech.basics.app.ProcessApp;
import com.wasitech.basics.classes.Basics;
import com.wasitech.basics.classes.Issue;
import com.wasitech.camera.activities.ImageListAct;
import com.wasitech.permission.Permission;
import com.wasitech.permission.PermissionGroup;
import com.wasitech.theme.Animations;
import com.wasitech.theme.Theme;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("MissingPermission")
public class CamApi2 extends AssistCompatActivity {
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    private final TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
            //open your camera here
            try {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                assert map != null;
                imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
                // Add permission for camera and let user grant the permission
                permission.groupRequest().cameraHead(PermissionGroup.TAKE_PIC);
            } catch (CameraAccessException e) {
                onFinish();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
            try {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                assert map != null;
                imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
                // Add permission for camera and let user grant the permission
            } catch (CameraAccessException e) {
                onFinish();
            }
        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
            try {
                updatePreview();
            } catch (Exception e) {
                onFinish();
            }
        }
    };
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            //This is called when the camera is open
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            closeCamera(Activity.RESULT_CANCELED);
        }
    };

    private CameraManager manager;

    public CamApi2() {
        super(R.layout.activity_camera);
    }

    protected void takePicture() {
        if (null == cameraDevice) {
            closeCamera(Activity.RESULT_CANCELED);
            return;
        }
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            ImageReader reader = ImageReader.newInstance(imageDimension.getWidth(), imageDimension.getHeight(), ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, captureRequestBuilder.get(CaptureRequest.CONTROL_EFFECT_MODE));
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getJpegOrientation(characteristics));
            ImageReader.OnImageAvailableListener readerListener = reader1 -> {
                Image image = reader1.acquireLatestImage();
                assert image != null;
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.capacity()];
                buffer.get(bytes);
                File file = Basics.Img.parseFile(bytes);
                Basics.Img.mediaScanner(getApplicationContext(), file.getPath());
                image.close();
            };
            reader.setOnImageAvailableListener(readerListener, null);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    createCameraPreview();
                    if (!isCamAction())
                        closeCamera(Activity.RESULT_OK);

                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, null);
                    } catch (CameraAccessException e) {
                        Issue.print(e, CamApi2.class.getName());
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                }
            }, null);
        } catch (CameraAccessException e) {
            Issue.print(e, CamApi2.class.getName());
            onFinish();
        }
    }

    protected void createCameraPreview() {
        try {

            SurfaceTexture texture = textureView.getSurfaceTexture();
            if (texture == null) return;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            // texture.setDefaultBufferSize(w, h);
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            ArrayList<Surface> list = new ArrayList<>();
            list.add(surface);
            cameraDevice.createCaptureSession(list, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(CamApi2.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            Issue.print(e, CamApi2.class.getName());
        }
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
            closeCamera(Activity.RESULT_CANCELED);
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            Issue.print(e, CamApi2.class.getName());
        }
    }

    private int getJpegOrientation(CameraCharacteristics c) {
        int deviceOrientation = getWindowManager().getDefaultDisplay().getRotation();
        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN)
            return 0;
        int sensorOrientation = 0;
        try {
            sensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;

        // Reverse device orientation for front-facing cameras
        boolean facingFront = false;
        try {
            facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
        if (facingFront) {
            deviceOrientation = -deviceOrientation;
        }
        return (sensorOrientation + deviceOrientation + 360) % 360;
    }

    private void closeCamera(int result) {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
        setResult(result);
        finishAndRemoveTask();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeCamera(Activity.RESULT_CANCELED);
    }

    private ImageView app;
    private TextureView textureView;
    private int cam;

    private boolean isCamAction() {
        String action = getIntent().getStringExtra("action");
        if (action != null)
            return action.equalsIgnoreCase("cam");
        else return false;
    }

    @Override
    public void setViews() {
        try {
            app = findViewById(R.id.app_img);
            textureView = findViewById(R.id.textureView);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setTheme() {
        try {
            Theme.ActivityNoTitle(this);
            Theme.imageView(app);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setValues() {
        try {
            app.setImageResource(ProcessApp.getPic());
            manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cam = getIntent().getIntExtra("id", CameraCharacteristics.LENS_FACING_FRONT);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setActions() {
        try {
            setPermission();
            setCamera(cam);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    @Override
    public void setExtras() {
        LinearLayout action_layout = findViewById(R.id.action_lay);
        HorizontalScrollView filter_layout = findViewById(R.id.filter_lay);
        ImageButton filter = findViewById(R.id.filter_btn);
        filter_layout.setVisibility(View.GONE);
        if (isCamAction()) {
            action_layout.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            ImageButton img = findViewById(R.id.images_btn);
            ImageButton capture = findViewById(R.id.capture_btn);
            ImageButton flip = findViewById(R.id.flip_btn);

            img.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ImageListAct.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
            flip.setOnClickListener(v -> {
                try {
                    Animator ltr = AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.ltr);
                    ltr.setTarget(flip);
                    ltr.start();

                    cam = (cam == 0 ? 1 : 0);
                    cameraId = manager.getCameraIdList()[cam];
                    cameraDevice.close();
                    manager.openCamera(cameraId, stateCallback, new Handler());
                } catch (Exception e) {
                    Issue.print(e, getClass().getName());
                }
            });
            capture.setOnClickListener(v -> {
                Animations.rotateItself(capture, 45);
                takePicture();
                Animations.shake(img, 200L);
            });
            filter.setOnClickListener(v -> filter_layout.setVisibility(filter_layout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));

        } else {
            action_layout.setVisibility(View.GONE);
            filter.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPermission() {
        permission = new PermissionGroup(CamApi2.this) {
            @Override
            public void onDenied(int code) {
                if (!isCamAction())
                    talk("Permission Denied. Cant take Pictures.");
                finish();
            }

            @Override
            public void requireRationaleAsk(String per, int code) {
                super.requireRationaleAsk(per, code);
                permission.displayRationale(
                        getString(R.string.s_cam_head) + " " + Permission.Talking.whichNotGranted(CamApi2.this, code)
                        , code
                        , !isCamAction());
            }

            @Override
            public void requireSimpleAsk(String per, int code) {
                super.requireSimpleAsk(per, code);
                permission.displayRationale(
                        getString(R.string.s_m_cam_head) + " " + Permission.Talking.whichNotGranted(CamApi2.this, code)
                        , code
                        , !isCamAction());
            }

            @SuppressLint("MissingPermission")
            @Override
            public void onGranted(int code) {
                try {
                    manager.openCamera(cameraId, stateCallback, new Handler());
                    if (!isCamAction())
                        new Handler().postDelayed(CamApi2.this::takePicture, 1500);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void neverAskAgain(int code) {
                talk("Grant " + Permission.Talking.whichNotGranted(CamApi2.this, code) + " permission.");
                startActivity(Permission.gotoSettings(getPackageName()).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                onFinish();
            }
        };
    }

    private void setCamera(int cam) {
        try {
            cameraId = manager.getCameraIdList()[cam];
        } catch (CameraAccessException e) {
            Issue.print(e, getClass().getName());
        }
        try {
            textureView.setSurfaceTextureListener(textureListener);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    private void onFinish() {
        if (!isCamAction())
            finish();
    }

    @SuppressLint("NonConstantResourceId")
    public void filterOnClick(View v) {
        switch (v.getId()) {
            case R.id.solaris: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_SOLARIZE);
                break;
            }
            case R.id.aqua: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_AQUA);
                break;
            }
            case R.id.sepia: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_SEPIA);
                break;
            }
            default:
            case R.id.none: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_OFF);
                break;
            }
            case R.id.postrize: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_POSTERIZE);
                break;
            }
            case R.id.black: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_BLACKBOARD);
                break;
            }
            case R.id.mono: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_MONO);
                break;
            }
            case R.id.negative: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_NEGATIVE);
                break;
            }
            case R.id.whiteboard: {
                captureRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, CameraMetadata.CONTROL_EFFECT_MODE_WHITEBOARD);
                break;
            }
        }
        updatePreview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //cameraId = manager.getCameraIdList()[cam];
            //cameraDevice.close();
            //manager.openCamera(cameraId, stateCallback, new Handler());
            if (textureView == null)
                textureView = findViewById(R.id.textureView);
            if (cameraDevice != null)
                cameraDevice.close();
            setCamera(cam);
        } catch (Exception e) {
            Issue.print(e, getClass().getName());
        }
    }

    public static class Intents {
        public static Intent takePic(Context context, int camId) {
            return new Intent(context, CamApi2.class)
                    .putExtra("id", camId)
                    .putExtra("action", "pic")
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        public static Intent OpenCam(Context context, int camId) {
            return new Intent(context, CamApi2.class)
                    .putExtra("id", camId)
                    .putExtra("action", "cam")
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }

    }

}