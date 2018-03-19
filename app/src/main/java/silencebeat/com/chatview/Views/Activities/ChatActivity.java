package silencebeat.com.chatview.Views.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.devlomi.record_view.OnRecordListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import silencebeat.com.chatview.Entities.Models.Comment;
import silencebeat.com.chatview.Modules.AudioRecordingListener;
import silencebeat.com.chatview.Modules.MainWireframe;
import silencebeat.com.chatview.Modules.OnItemCommentClickListener;
import silencebeat.com.chatview.R;
import silencebeat.com.chatview.Supports.Utils.AudioRecorder;
import silencebeat.com.chatview.Supports.Utils.ChatAdapter;
import silencebeat.com.chatview.Supports.Utils.Debug;
import silencebeat.com.chatview.Supports.Utils.ImageChoser;
import silencebeat.com.chatview.Supports.Utils.StaticVariable;
import silencebeat.com.chatview.Views.Activities.MediaActivity.CameraActivity;
import silencebeat.com.chatview.Views.Fragments.DetailFotoDialogFragment;
import silencebeat.com.chatview.databinding.ActivityChatBinding;

/**
 * Created by Candra Triyadi on 05/03/2018.
 */

public class ChatActivity extends AppCompatActivity implements OnItemCommentClickListener
        , AudioRecordingListener{

    ActivityChatBinding binding;
    ChatAdapter chatAdapter;
    ImageChoser imageChoser;
    AudioRecorder audioRecorder;
    MediaPlayer mediaPlayer;
    String userId = "-1";
    String friendId = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        imageChoser = new ImageChoser(this, this);
        audioRecorder = new AudioRecorder(this, this, this);
        setView();
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }


    private void setView(){
        chatAdapter = new ChatAdapter(this, new ArrayList<Comment>(), this);
        chatAdapter.setUserId(userId);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(chatAdapter);

        binding.editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.btnSend.setVisibility(View.GONE);
                    binding.btnRecord.setVisibility(View.VISIBLE);
                }else{
                    binding.btnSend.setVisibility(View.VISIBLE);
                    binding.btnRecord.setVisibility(View.GONE);
                }
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment(binding.editMessage.getText().toString(), null);
            }
        });

        binding.recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                audioRecorder.requestAudioPermission();
                binding.editMessage.setVisibility(View.GONE);
                binding.btnGallery.setVisibility(View.GONE);
                binding.btnCamera.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {
                audioRecorder.stopRecording(false);
                binding.editMessage.setVisibility(View.VISIBLE);
                binding.btnGallery.setVisibility(View.VISIBLE);
                binding.btnCamera.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish(long recordTime) {
                audioRecorder.stopRecording(true);
                binding.editMessage.setVisibility(View.VISIBLE);
                binding.btnGallery.setVisibility(View.VISIBLE);
                binding.btnCamera.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLessThanSecond() {

            }
        });

        binding.recordView.setCancelBounds(130);
        binding.recordView.setSoundEnabled(true);
        binding.recordView.setLessThanSecondAllowed(false);
        binding.btnRecord.setRecordView(binding.recordView);

        binding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, CameraActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        binding.btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChoser.permissionWriteExternalGalery();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemCommentClicked(@NotNull Comment comment) {

        if (comment.getFileUrl().contains(".jpg")){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            DetailFotoDialogFragment detailFotoDialogFragment = DetailFotoDialogFragment.getInstance(comment.getFileUrl());
            detailFotoDialogFragment.show(ft, "");
        }else if (comment.getFileUrl().contains(".mp4")){
            MainWireframe.getInstance().toPlayVideoView(this, comment.getFileUrl());
        }else{
            mediaPlayer.stop();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case ImageChoser.PERMISSION_CODE_CAMERA:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    imageChoser.Camera();
                }
                break;
            case ImageChoser.PERMIISION_CODE_WRITE2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    imageChoser.Gallery();
                }
                break;
            case AudioRecorder.PERMISSIONS_RECORD_AUDIO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode == ImageChoser.RESULT_CODE_GALLERY){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                sendComment("", StaticVariable.savebitmap(this, bitmap));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == 10){
            String path = data.getStringExtra("filepath");
            if (path != null){
                if (path.contains(".mp4")){
//                    Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                    sendComment("", path);
                }else {
                    sendComment("", path);
                }
            }
        }
    }

    @Override
    public void onFinishRecording(@NotNull String filePath) {
        sendComment("", filePath);
    }

    @Override
    protected void onPause() {
        audioRecorder.release();
        if (mediaPlayer != null)
            mediaPlayer.stop();
        super.onPause();
    }


    private void sendComment(String noteText, String fileUrl){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String time = sdf.format(new Date());

        if (fileUrl != null && !fileUrl.isEmpty()
                && !fileUrl.equalsIgnoreCase("null")){
            chatAdapter.addItem(new Comment(userId, noteText, time, fileUrl, ""));
            Debug.log("FileURL", fileUrl);
        }else{
            chatAdapter.addItem(new Comment(userId, noteText, time, null
                    , null));
        }
        binding.editMessage.setText("");
        binding.list.smoothScrollToPosition(chatAdapter.getItemCount() - 1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dummyReply();
            }
        }, 1000);
    }

    private void dummyReply(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String time = sdf.format(new Date());
        chatAdapter.addItem(new Comment(friendId, "hallo", time, null
                , null));
        binding.list.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
    }


}
