package silencebeat.com.chatview.Views.ViewHolders.Incoming;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import silencebeat.com.chatview.Entities.Models.Comment;
import silencebeat.com.chatview.Modules.OnItemCommentClickListener;
import silencebeat.com.chatview.Supports.Utils.StaticVariable;
import silencebeat.com.chatview.databinding.ItemChatTextIncomingBinding;

/**
 * Created by Candra Triyadi on 15/03/2018.
 */

public class IncomingChatTextViewHolder extends RecyclerView.ViewHolder {

    private ItemChatTextIncomingBinding binding;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    Calendar calendar = Calendar.getInstance();

    public IncomingChatTextViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void onBind(Context context, Comment comment, OnItemCommentClickListener listener){
        binding.txtMessage.setText(comment.getNoteText());

        try {
            Date date = sdf.parse(comment.getNoteDate());
            calendar.setTime(date);
            String time = StaticVariable.parseDate(context, calendar);
            binding.txtTime.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
