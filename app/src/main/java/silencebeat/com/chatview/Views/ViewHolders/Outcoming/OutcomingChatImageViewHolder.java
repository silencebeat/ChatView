package silencebeat.com.chatview.Views.ViewHolders.Outcoming;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import silencebeat.com.chatview.Entities.Models.Comment;
import silencebeat.com.chatview.Modules.OnItemCommentClickListener;
import silencebeat.com.chatview.Supports.Utils.StaticVariable;
import silencebeat.com.chatview.databinding.ItemChatImageOutcomingBinding;

/**
 * Created by Candra Triyadi on 15/03/2018.
 */

public class OutcomingChatImageViewHolder extends RecyclerView.ViewHolder{

    ItemChatImageOutcomingBinding binding;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    Calendar calendar = Calendar.getInstance();

    public OutcomingChatImageViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void onBind(Context context, final Comment comment, final OnItemCommentClickListener listener){
        try {
            Date date = sdf.parse(comment.getNoteDate());
            calendar.setTime(date);
            String time = StaticVariable.parseDate(context, calendar);
            binding.txtTime.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(comment.getFileUrl())
                .asBitmap()
                .override(100,100 )
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgPhoto);


        binding.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemCommentClicked(comment);
            }
        });
    }
}
