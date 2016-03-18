package com.lftechnology.hamropay.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.events.ChatEvents;
import com.lftechnology.hamropay.fragment.StickerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ManasShrestha on 2/18/16.
 */
public class StickerView extends RelativeLayout {
    private ChatEvents chatEvents;

    @Bind(R.id.iv_sticker_view)
    ImageView ivSticker;

    @Bind(R.id.rl_container)
    RelativeLayout relativeLayout;

    @Bind(R.id.tv_date)
    TextView tvDate;

    public StickerView(Context context) {
        this(context, null, 0);
    }

    public StickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.sticker_view, this, true);

        ButterKnife.bind(this);

        chatEvents = (ChatEvents) context;
    }

    public void setData(MessageModel messageModel) {
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (!messageModel.isReceived) {
            relativeLayoutParams.addRule(ALIGN_PARENT_END);
        } else {
            relativeLayoutParams.addRule(ALIGN_PARENT_START);
        }

        Glide.with(getContext()).load(StickerAdapter.stickerList[messageModel.stickerDrawable])
                .into(ivSticker);

        tvDate.setText(messageModel.date);

        relativeLayout.setLayoutParams(relativeLayoutParams);
    }
}
