package silencebeat.com.chatview.Supports.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import java.io.File;

/**
 * Created by Candra Triyadi on 10/21/16.
 */

public class ImageChoser {
    Context context;
    Activity activity;

    public final static int PERMISSION_CODE_CAMERA = 123;
    public final static int RESULT_CODE_CAMERA  = 111;
    public final static int PERMIISION_CODE_WRITE2 = 789;
    public final static int RESULT_CODE_GALLERY = 222;
    public final static int PERMISSION_CODE_VIDEO = 963;
    public final static int RESULT_CODE_VIDEO = 852;

    public ImageChoser(Context context, Activity activity) {
        this.context    = context;
        this.activity   = activity;
    }

    public void permissionCamera(){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},PERMISSION_CODE_CAMERA);
        }else {
            this.Camera();
        }
    }

    public void permissionWriteExternalGalery(){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMIISION_CODE_WRITE2);
        }else {
            Gallery();
        }
    }

    public void Camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, RESULT_CODE_CAMERA);
    }

    public void Gallery(){
        File path = new File(Environment.getExternalStorageDirectory() + "/");
        if (!path.exists()) {
            path.mkdirs();
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, RESULT_CODE_GALLERY);
    }

}
