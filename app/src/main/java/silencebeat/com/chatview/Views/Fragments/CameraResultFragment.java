package silencebeat.com.chatview.Views.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import silencebeat.com.chatview.R;
import silencebeat.com.chatview.Supports.Utils.StaticVariable;
import silencebeat.com.chatview.databinding.FragmentCameraResultBinding;


/**
 * Created by Candra Triyadi on 07/03/2018.
 */

public class CameraResultFragment extends DialogFragment {

    public interface OnCameraResultListener{
        void onResultTaken(String pathUrl);
        void onCancel();
    }

    OnCameraResultListener listener;
    FragmentCameraResultBinding binding;
    String type, pathUrl;
    public static final String TYPE_PHOTO = "TYPE_PHOTO";
    public static final String TYPE_VIDEO = "TYPE_VIDEO";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnCameraResultListener) context;
    }

    public static CameraResultFragment newInstance(String pathUrl, String type){
        CameraResultFragment fragment = new CameraResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pathUrl", pathUrl);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        type = getArguments().getString("type");
        pathUrl = getArguments().getString("pathUrl");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera_result, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (type.equalsIgnoreCase(TYPE_PHOTO)){
            binding.imgPhoto.setVisibility(View.VISIBLE);
            Bitmap myBitmap = BitmapFactory.decodeFile(pathUrl);
            binding.imgPhoto.setImageBitmap(StaticVariable.RotateBitmap(myBitmap, 90));
        }else{
            binding.videoPreview.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(pathUrl);
            binding.videoPreview.setVideoURI(uri);
            binding.videoPreview.start();
        }

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onCancel();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onResultTaken(pathUrl);
            }
        });
    }

}
