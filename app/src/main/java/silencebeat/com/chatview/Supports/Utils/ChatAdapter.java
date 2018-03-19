package silencebeat.com.chatview.Supports.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import silencebeat.com.chatview.Entities.Models.Comment;
import silencebeat.com.chatview.Modules.OnItemCommentClickListener;
import silencebeat.com.chatview.R;
import silencebeat.com.chatview.Views.ViewHolders.Incoming.IncomingChatAudioViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Incoming.IncomingChatImageViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Incoming.IncomingChatTextViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Incoming.IncomingChatVideoViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Outcoming.OutcomingChatAudioViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Outcoming.OutcomingChatImageViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Outcoming.OutcomingChatTextViewHolder;
import silencebeat.com.chatview.Views.ViewHolders.Outcoming.OutcomingChatVideoViewHolder;

/**
 * Created by Candra Triyadi on 05/03/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int INCOMING_TEXT = 10;
    private final static int INCOMING_IMAGE = 11;
    private final static int INCOMING_AUDIO = 12;
    private final static int INCOMING_VIDEO = 13;

    private final static int OUTCOMING_TEXT = 20;
    private final static int OUTCOMING_IMAGE = 21;
    private final static int OUTCOMING_AUDIO = 22;
    private final static int OUTCOMING_VIDEO = 23;

    private List<Comment> list;
    private OnItemCommentClickListener listener;
    private Context context;
    private String userID;
    MediaPlayer mediaPlayer;

    public ChatAdapter(Context context, List<Comment> list, OnItemCommentClickListener listener){
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    public void setUserId(String userId){
        this.userID = userId;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setList(List<Comment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(Comment chat){
        list.add(chat);
        notifyItemInserted(list.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType){
            case INCOMING_AUDIO:
                layout = R.layout.item_chat_audio_incoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new IncomingChatAudioViewHolder(view);
                break;
            case INCOMING_TEXT:
                layout = R.layout.item_chat_text_incoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new IncomingChatTextViewHolder(view);
                break;
            case INCOMING_IMAGE:
                layout = R.layout.item_chat_image_incoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new IncomingChatImageViewHolder(view);
                break;
            case INCOMING_VIDEO:
                layout = R.layout.item_chat_video_incoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new IncomingChatVideoViewHolder(view);
                break;
            case OUTCOMING_AUDIO:
                layout = R.layout.item_chat_audio_outcoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new OutcomingChatAudioViewHolder(view);
                break;
            case OUTCOMING_TEXT:
                layout = R.layout.item_chat_text_outcoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new OutcomingChatTextViewHolder(view);
                break;
            case OUTCOMING_IMAGE:
                layout = R.layout.item_chat_image_outcoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new OutcomingChatImageViewHolder(view);
                break;
            case OUTCOMING_VIDEO:
                layout = R.layout.item_chat_video_outcoming;
                view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                viewHolder = new OutcomingChatVideoViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        Comment chat = list.get(position);
        switch (viewType){
            case INCOMING_AUDIO:
                ((IncomingChatAudioViewHolder) holder).onBind(context, chat, listener);
                break;
            case INCOMING_TEXT:
                ((IncomingChatTextViewHolder) holder).onBind(context, chat, listener);
                break;
            case INCOMING_IMAGE:
                ((IncomingChatImageViewHolder) holder).onBind(context, chat, listener);
                break;
            case INCOMING_VIDEO:
                ((IncomingChatVideoViewHolder) holder).onBind(context, chat, listener);
                break;
            case OUTCOMING_AUDIO:
                ((OutcomingChatAudioViewHolder) holder).onBind(context, chat, listener);
                break;
            case OUTCOMING_TEXT:
                ((OutcomingChatTextViewHolder) holder).onBind(context, chat, listener);
                break;
            case OUTCOMING_IMAGE:
                ((OutcomingChatImageViewHolder) holder).onBind(context, chat, listener);
                break;
            case OUTCOMING_VIDEO:
                ((OutcomingChatVideoViewHolder) holder).onBind(context, chat, listener);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Comment chat = list.get(position);
        if (chat.getAdminId().equalsIgnoreCase(userID)){
            if (chat.getFileUrl() == null){
                return OUTCOMING_TEXT;
            }else if (chat.getFileUrl().contains(".mp4")){
                return OUTCOMING_VIDEO;
            }else if (chat.getFileUrl().contains(".jpg")){
                return OUTCOMING_IMAGE;
            }else{
                return OUTCOMING_AUDIO;
            }

        }else{
            if (chat.getFileUrl() == null){
                return INCOMING_TEXT;
            }else if (chat.getFileUrl().contains(".mp4")){
                return INCOMING_VIDEO;
            }else if (chat.getFileUrl().contains(".jpg")){
                return INCOMING_IMAGE;
            }else{
                return INCOMING_AUDIO;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Comment> getList() {
        return list;
    }
}
