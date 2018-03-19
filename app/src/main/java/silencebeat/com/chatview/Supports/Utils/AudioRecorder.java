package silencebeat.com.chatview.Supports.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import silencebeat.com.chatview.Modules.AudioRecordingListener;

/**
 * Created by Candra Triyadi on 05/03/2018.
 */

public class AudioRecorder {

    public static final int PERMISSIONS_RECORD_AUDIO = 9876;
    Activity activity;
    Context context;
    MediaRecorder mRecorder;
    private File mOutputFile;
    AudioRecordingListener listener;
    boolean isStartRecording = false;

    public AudioRecorder(Activity activity, Context context, AudioRecordingListener listener){
        this.activity = activity;
        this.listener = listener;
        this.context = context;
    }

    public boolean requestAudioPermission(){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            RecordAudio();
            return true;
        }else{
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSIONS_RECORD_AUDIO);

            return false;
        }
    }

    public void RecordAudio(){
        if (mRecorder == null){
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        }


        mOutputFile = getOutputFile();
        mOutputFile.getParentFile().mkdirs();
        mRecorder.setOutputFile(mOutputFile.getAbsolutePath());

        if (!isStartRecording){
            try {
                mRecorder.prepare();
                mRecorder.start();
                isStartRecording = true;
                Log.d("Voice Recorder","started recording to "+mOutputFile.getAbsolutePath());
            } catch (IOException e) {
                mRecorder.release();
                Log.e("Voice Recorder", "prepare() failed "+e.getMessage());
            }
        }else{
            isStartRecording = false;
            try{
                mRecorder.stop();
                mRecorder.reset();
                mRecorder.release();
                mRecorder = null;
            }catch (Exception e){
                Debug.log("voice recorder", "stop failed "+e.getMessage());
            }

        }

    }

    public void stopRecording(boolean saveFile) {
        if (mRecorder != null){
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            isStartRecording = false;
            if (!saveFile && mOutputFile != null) {
                mOutputFile.delete();
            }else{
                listener.onFinishRecording(mOutputFile.getPath());
            }
        }
    }

    private File getOutputFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US);
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/"+activity.getPackageName()
                + "/Voice_Recorder/RECORDING_"
                + dateFormat.format(new Date())
                + ".amr");
    }

    public void release(){
        stopRecording(false);
    }
}
