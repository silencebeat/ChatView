package silencebeat.com.chatview.Views.ViewHolders.Outcoming;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import silencebeat.com.chatview.Entities.Models.Comment;
import silencebeat.com.chatview.Modules.OnItemCommentClickListener;
import silencebeat.com.chatview.R;
import silencebeat.com.chatview.Supports.Utils.StaticVariable;
import silencebeat.com.chatview.Views.Activities.ChatActivity;
import silencebeat.com.chatview.databinding.ItemChatAudioOutcomingBinding;

/**
 * Created by Candra Triyadi on 15/03/2018.
 */

public class OutcomingChatAudioViewHolder extends RecyclerView.ViewHolder {

    ItemChatAudioOutcomingBinding binding;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    Calendar calendar = Calendar.getInstance();
    private int lenght = 0;

    public OutcomingChatAudioViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void onBind(final Context context, final Comment comment,
                       final OnItemCommentClickListener listener){

        try {
            Date date = sdf.parse(comment.getNoteDate());
            calendar.setTime(date);
            String time = StaticVariable.parseDate(context, calendar);
            binding.txtTime.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final MediaPlayer mediaPlayer = new MediaPlayer();
        final Handler mSeekbarUpdateHandler = new Handler();

        try {
            mediaPlayer.setDataSource(comment.getFileUrl());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        int duration = mediaPlayer.getDuration();
        binding.seekbar.setMax(100);


        final Runnable mUpdateSeekbar = new Runnable() {

            @Override
            public void run() {

                String time = String.format(Locale.US, "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()),
                        TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()))
                );

                float a =  (float) mediaPlayer.getCurrentPosition()/ (float)mediaPlayer.getDuration();
                binding.txtTimePlay.setText(time);
                binding.seekbar.setProgress((int)(a*100f));
                mSeekbarUpdateHandler.postDelayed(this, 15);
            }
        };

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                lenght = 0;
                binding.btnPlay.setImageResource(R.drawable.ic_play);
                mSeekbarUpdateHandler.removeCallbacksAndMessages(null);
            }
        });

        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekbarUpdateHandler.removeCallbacksAndMessages(null);
                setMediaPlayer(context, mediaPlayer);
                if (mediaPlayer.isPlaying()){
                    lenght = mediaPlayer.getCurrentPosition();
                    binding.btnPlay.setImageResource(R.drawable.ic_play);
                    mediaPlayer.pause();

                }else{
                    binding.btnPlay.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.seekTo(lenght);
                    mediaPlayer.start();
                    mediaPlayer.setVolume(100,100);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 15);
                }
            }
        });

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                    mSeekbarUpdateHandler.removeCallbacksAndMessages(null);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setMediaPlayer(Context context, MediaPlayer mediaPlayer){
        ((ChatActivity)context).setMediaPlayer(mediaPlayer);
    }
}
