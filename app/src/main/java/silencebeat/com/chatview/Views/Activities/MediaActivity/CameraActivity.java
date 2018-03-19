package silencebeat.com.chatview.Views.Activities.MediaActivity;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.CameraFragmentApi;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener;
import com.github.florent37.camerafragment.listeners.CameraFragmentStateListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import silencebeat.com.chatview.R;
import silencebeat.com.chatview.Views.Fragments.CameraResultFragment;
import silencebeat.com.chatview.databinding.ActivityCameraBinding;

/**
 * Created by Candra Triyadi on 07/03/2018.
 */

public class CameraActivity extends AppCompatActivity implements CameraFragmentResultListener
        , CameraResultFragment.OnCameraResultListener {

    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    public static final String FRAGMENT_TAG = "camera";

    ActivityCameraBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 15) {
            final String[] permissions = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};

            final List<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
            } else addCamera();
        } else {
            addCamera();
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void addCamera() {

        final Configuration.Builder builder = new Configuration.Builder();
        builder
                .setCamera(Configuration.CAMERA_FACE_REAR)
                .setFlashMode(Configuration.FLASH_MODE_OFF)
                .setMediaAction(Configuration.MEDIA_ACTION_PHOTO);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final CameraFragment cameraFragment = CameraFragment.newInstance(builder.build());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, cameraFragment, FRAGMENT_TAG)
                .commit();

        if (cameraFragment != null) {
            cameraFragment.setResultListener(this);
            cameraFragment.setStateListener(new CameraFragmentStateListener() {

                @Override
                public void onCurrentCameraBack() {
                    binding.btnCameraSwitch.displayBackCamera();
                }

                @Override
                public void onCurrentCameraFront() {
                    binding.btnCameraSwitch.displayFrontCamera();
                }

                @Override
                public void onFlashAuto() {
                    binding.btnFlashSwitch.displayFlashAuto();
                }

                @Override
                public void onFlashOn() {
                    binding.btnFlashSwitch.displayFlashOn();
                }

                @Override
                public void onFlashOff() {
                    binding.btnFlashSwitch.displayFlashOff();
                }

                @Override
                public void onCameraSetupForPhoto() {
                    binding.btnFlashSwitch.setVisibility(View.VISIBLE);
                    binding.btnRecord.displayPhotoState();
                    binding.btnSwitchAction.displayActionWillSwitchVideo();
                }

                @Override
                public void onCameraSetupForVideo() {
                    binding.btnSwitchAction.displayActionWillSwitchPhoto();
                    binding.btnRecord.displayVideoRecordStateReady();
                    binding.btnFlashSwitch.setVisibility(View.GONE);
                }

                @Override
                public void shouldRotateControls(int degrees) {
                    binding.btnRecord.setRotation(degrees);
                    binding.btnSwitchAction.setRotation(degrees);
                    binding.btnRecord.setRotation(degrees);
                    binding.btnCameraSwitch.setRotation(degrees);
                    binding.btnFlashSwitch.setRotation(degrees);
                    binding.btnSetting.setRotation(degrees);
                }

                @Override
                public void onRecordStateVideoReadyForRecord() {
                    binding.btnRecord.displayVideoRecordStateReady();
                }

                @Override
                public void onRecordStateVideoInProgress() {
                    binding.btnRecord.displayVideoRecordStateInProgress();
                }

                @Override
                public void onRecordStatePhoto() {
                    binding.btnRecord.displayPhotoState();
                }

                @Override
                public void onStopVideoRecord() {
                    binding.btnRecord.displayVideoRecordStateReady();
                }

                @Override
                public void onStartVideoRecord(File outputFile) {
                }
            });
        }

        binding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CameraFragmentApi cameraFragment = getCameraFragment();
                if (cameraFragment != null)
                    cameraFragment.openSettingDialog();
            }
        });

        binding.btnCameraSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CameraFragmentApi cameraFragment = getCameraFragment();
                if (cameraFragment != null)
                    cameraFragment.switchCameraTypeFrontBack();
            }
        });

        binding.btnFlashSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CameraFragmentApi cameraFragment = getCameraFragment();
                if (cameraFragment != null)
                    cameraFragment.toggleFlashMode();
            }
        });

        binding.btnSwitchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CameraFragmentApi cameraFragment = getCameraFragment();
                if (cameraFragment != null)
                    cameraFragment.switchActionPhotoVideo();
            }
        });

        binding.btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CameraFragmentApi cameraFragment = getCameraFragment();
                if (cameraFragment != null){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US);
                    cameraFragment.takePhotoOrCaptureVideo(CameraActivity.this, Environment
                                    .getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + getPackageName()+ File.separator +"temp/",
                            dateFormat.format(new Date()));
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            finish();
        }
    }

    private CameraFragmentApi getCameraFragment() {
        return (CameraFragmentApi) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    @Override
    public void onVideoRecorded(String filePath) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        CameraResultFragment fragment = CameraResultFragment.newInstance(filePath, CameraResultFragment.TYPE_VIDEO);
        fragment.show(ft, "");
    }

    @Override
    public void onPhotoTaken(byte[] bytes, String filePath) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        CameraResultFragment fragment = CameraResultFragment.newInstance(filePath, CameraResultFragment.TYPE_PHOTO);
        fragment.show(ft, "");
    }

    @Override
    public void onResultTaken(String pathUrl) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("filepath", pathUrl);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onCancel() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            addCamera();
        }
    }
}
