package silencebeat.com.chatview.Modules;

import android.content.Context;
import android.content.Intent;

import silencebeat.com.chatview.Views.Activities.ChatActivity;
import silencebeat.com.chatview.Views.Activities.MediaActivity.VideoPlayerActivity;

/**
 * Created by Candra Triyadi on 27/02/2018.
 */

public class MainWireframe {

    private MainWireframe(){
    }

    private static class SingletonHelper{
        private static final MainWireframe INSTANCE = new MainWireframe();
    }

    public static MainWireframe getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void toChatView(Context context, String param){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("param", param);
        context.startActivity(intent);
    }


    public void toPlayVideoView(Context context, String videoURL){
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("videoURL", videoURL);
        context.startActivity(intent);
    }

}
