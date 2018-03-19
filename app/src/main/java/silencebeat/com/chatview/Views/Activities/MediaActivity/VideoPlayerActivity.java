package silencebeat.com.chatview.Views.Activities.MediaActivity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;

import silencebeat.com.chatview.R;
import silencebeat.com.chatview.databinding.ActivityVideoPlayerBinding;

/**
 * Created by Candra Triyadi on 16/03/2018.
 */

public class VideoPlayerActivity extends AppCompatActivity implements EasyVideoCallback {

    ActivityVideoPlayerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player);
        String videoURL = getIntent().getStringExtra("videoURL");
        binding.player.setCallback(this);
        binding.player.setSource(Uri.parse(videoURL));
        binding.player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.player.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

        Toast.makeText(this,"Can't play this video", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {

    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }
}
