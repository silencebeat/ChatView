package silencebeat.com.chatview.Views.Fragments;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import silencebeat.com.chatview.R;
import silencebeat.com.chatview.databinding.FragmentDetailFotoDialogBinding;

/**
 * Created by Candra Triyadi on 06/03/2018.
 */

public class DetailFotoDialogFragment extends DialogFragment {

    FragmentDetailFotoDialogBinding binding;

    public static DetailFotoDialogFragment getInstance(String path){
        DetailFotoDialogFragment fragment = new DetailFotoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_foto_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        String path = getArguments().getString("path");
        Glide.with(getActivity())
                .load(path)
                .asBitmap()
                .override(width,height)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.img);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        try {
//            ExifInterface exif = new ExifInterface(path);
//            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            int rotationInDegrees = exifToDegrees(rotation);
//            Matrix matrix = new Matrix();
//            if (rotation != 0f) {matrix.preRotate(rotationInDegrees);}
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        binding.img.setImageBitmap(bitmap);
//
//

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

//    private static int exifToDegrees(int exifOrientation) {
//        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
//        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
//        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
//        return 0;
//    }
}
