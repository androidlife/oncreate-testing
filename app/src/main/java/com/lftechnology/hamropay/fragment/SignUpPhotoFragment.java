package com.lftechnology.hamropay.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.fragment.base.BaseFragment;
import com.lftechnology.hamropay.interfaces.OnFragmentInteractionListener;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.model.UserInfo;
import com.lftechnology.hamropay.utils.GeneralUtils;
import com.lftechnology.hamropay.utils.ResourceUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPhotoFragment extends BaseFragment {

    private static final String USER_INFO = "user_info";
    public static final int PICTURE_REQUEST_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 102;
    private static String imagePath;

    @Bind(R.id.tv_sign_up_photo_tagline)
    TextView tvSignUpPhotoTagline;
    @Bind(R.id.iv_camera)
    ImageView ivCamera;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.civ_profilepic)
    ImageView civProfilepic;
    @Bind(R.id.tv_sign_up_photo_hint)
    TextView tvSignUpPhotoHint;
    @Bind(R.id.btn_continue)
    Button btnContinue;
    @Bind(R.id.tv_sign_up_photo_skip)
    TextView tvSignUpPhotoSkip;

    private UserInfo userInfo;
    private OnFragmentInteractionListener mListener;
    private Context context;

    public SignUpPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userInfo Parameter 1.
     * @return A new instance of fragment SignUpPhotoFragment.
     */
    public static SignUpPhotoFragment newInstance(UserInfo userInfo) {
        SignUpPhotoFragment fragment = new SignUpPhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_INFO, userInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userInfo = getArguments().getParcelable(USER_INFO);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up_photo;
    }

    @OnClick({R.id.btn_continue, R.id.tv_sign_up_photo_skip, R.id.civ_profilepic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                if (mListener != null) {
                    userInfo.setPicLocation(imagePath);
                    mListener.onFragmentInteraction(userInfo, Constants.SIGN_UP_OTHERS_FRAGMENT);
                    saveUserInfo();
                }
                break;
            case R.id.tv_sign_up_photo_skip:
                if (mListener != null) {
                    mListener.onFragmentInteraction(userInfo, Constants.SIGN_UP_OTHERS_FRAGMENT);
                    saveUserInfo();
                }
                break;
            case R.id.civ_profilepic:
                selectImage();
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment
                            .getExternalStorageDirectory(), "profile_picture.jpg");
                    imagePath = file.getAbsolutePath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK/*,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI*/);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSignUpPhotoTagline.setText(String.format("Hi %s, upload a photo of yourself.", GeneralUtils.convertToUpperCase(userInfo.getName())));
        if (imagePath != null) {
            Glide.with(getContext()).load(imagePath).centerCrop().signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).into(civProfilepic);
            ivCamera.setVisibility(View.GONE);
        }
        if (ResourceUtils.getScreenHeight() < 1800) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) civProfilepic.getLayoutParams();
            layoutParams.width = ResourceUtils.dpToPixels(80);
            layoutParams.height = ResourceUtils.dpToPixels(80);
            civProfilepic.setLayoutParams(layoutParams);
            civProfilepic.requestLayout();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivCamera.getLayoutParams();
            params.width = ResourceUtils.dpToPixels(24);
            params.height = ResourceUtils.dpToPixels(24);
            ivCamera.setLayoutParams(params);
            ivCamera.requestLayout();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri selectedImageUri = data.getData();
                imagePath = selectedImageUri.toString();
            } else if (requestCode == PICTURE_REQUEST_CODE) {
                final File file = new File(imagePath);
                if (file.exists()) {
                    // invoke the system's media scanner to add your photo to the Media Provider's database,
                    // making it available in the Android Gallery application and to other apps
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    context.sendBroadcast(mediaScanIntent);
                }

            }
        }
    }

    private void saveUserInfo() {
        DbManager.getInstance().registerNewUser(userInfo);
    }
}
