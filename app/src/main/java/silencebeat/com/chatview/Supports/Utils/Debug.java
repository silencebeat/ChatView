package silencebeat.com.chatview.Supports.Utils;

import android.util.Log;

import silencebeat.com.chatview.BuildConfig;

/**
 * Created by Candra Triyadi on 07/11/2017.
 */

public class Debug {

    public static void log(String TAG, String message){
        if (BuildConfig.DEBUG){
            Log.e(TAG, message);
        }
    }
}
