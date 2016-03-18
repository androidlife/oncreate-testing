package com.lftechnology.hamropay.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ManasShrestha on 2/10/16.
 */
public class PhotoMessage extends RelativeLayout {

    @Bind(R.id.iv_photo_sticker)
    ImageView ivPhotoSticker;

    @Bind(R.id.tv_photo_text)
    TextView tvPhotoText;

    @Bind(R.id.tv_date)
    TextView tvDate;

    @Bind(R.id.rl_container)
    RelativeLayout rlContainer;


    public PhotoMessage(Context context) {
        this(context, null, 0);
    }

    public PhotoMessage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoMessage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_photo_message, this, true);
        ButterKnife.bind(this);


    }

    public void setData(MessageModel messageModel) {
        Log.e("----", "setDataPhotowith message");
        LayoutParams relativeLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setLayoutParams(relativeLayoutParams);
        int backgroundChatDrawable;

        if (!messageModel.isReceived) {
            backgroundChatDrawable = R.drawable.chat_bubble_right_aligned;
            relativeLayoutParams.addRule(ALIGN_PARENT_END);
            tvPhotoText.setTextColor(Color.parseColor("#000000"));
        } else {
            backgroundChatDrawable = R.drawable.chat_bubble_left_aligned;
            relativeLayoutParams.addRule(ALIGN_PARENT_START);
            tvPhotoText.setTextColor(Color.parseColor("#ffffff"));
        }

        rlContainer.setLayoutParams(relativeLayoutParams);

        Bitmap mapBitmapScaled = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable);
        byte[] chunk = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable).getNinePatchChunk();
        NinePatchDrawable mapNinePatch = new NinePatchDrawable(getResources(),
                mapBitmapScaled, chunk, new Rect(), null);
        rlContainer.setBackground(mapNinePatch);

        tvDate.setText(messageModel.date);
        if (messageModel.textMessage != null) {
            tvPhotoText.setText(messageModel.textMessage);
        } else {
            tvPhotoText.setVisibility(GONE);
        }

        if (messageModel.baseString == null) {
            Glide.with(getContext()).load(messageModel.photoUri)
                    .centerCrop().into(ivPhotoSticker);
        } else {
            byte[] decodedString = Base64.decode(messageModel.baseString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivPhotoSticker.setImageBitmap(decodedByte);
        }
    }

}
