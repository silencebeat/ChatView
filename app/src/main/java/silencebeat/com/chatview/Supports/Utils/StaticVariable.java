package silencebeat.com.chatview.Supports.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.format.DateUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import silencebeat.com.chatview.R;

/**
 * Created by Candra Triyadi on 07/03/2018.
 */

public class StaticVariable {


    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static String savebitmap(Activity activity, Bitmap bmp) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + activity.getPackageName()+ File.separator +"temp/"+dateFormat.format(new Date())+".jpg");
        f.getParentFile().mkdirs();
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f.getAbsolutePath();
    }

    public static String parseDate(Context context, Calendar calendar){
        String time;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (DateUtils.isToday(calendar.getTimeInMillis())){
            time = hour+":"+minute;
        }else{
            time = context.getResources().getStringArray(R.array.arr_month_short)[month] +
                    " "+day+", "+year;
        }
        return time;
    }

    public static String toDate(Context context, String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        String time;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        time = context.getResources().getStringArray(R.array.arr_month)[month] +
                " "+day+", "+year;
        return time;
    }
}
