package com.lftechnology.hamropay.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} for simple text messages
 */
public class SimpleMessageViewHolder extends RecyclerView.ViewHolder {

    private int backgroundChatDrawable;
    private Context context;

    @Bind(R.id.tv_text_message)
     TextView tvTextMessage;

    @Bind(R.id.tv_date)
     TextView tvDate;

    public SimpleMessageViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.context = context;

    }

    public void setData(MessageModel messageModel){

        if (!messageModel.isReceived) {
            backgroundChatDrawable = R.drawable.chat_bubble_right_aligned;
        } else {
            backgroundChatDrawable = R.drawable.chat_bubble_left_aligned;
        }

        Bitmap mapBitmapScaled = BitmapFactory.decodeResource(context.getResources(), backgroundChatDrawable);
        // Load the 9-patch data chunk and apply to the view
        byte[] chunk = BitmapFactory.decodeResource(context.getResources(), backgroundChatDrawable).getNinePatchChunk();
        NinePatchDrawable mapNinePatch = new NinePatchDrawable(context.getResources(),
                mapBitmapScaled, chunk, new Rect(), null);
        tvTextMessage.setBackground(mapNinePatch);

        if(!messageModel.isReceived) {
            RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            rlParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            tvTextMessage.setLayoutParams(rlParams);
            tvTextMessage.setTextColor(Color.parseColor("#000000"));
        }else {
            RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            rlParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            tvTextMessage.setLayoutParams(rlParams);
            tvTextMessage.setTextColor(Color.parseColor("#ffffff"));
        }

        tvDate.setText(messageModel.date);
        tvTextMessage.setText(messageModel.textMessage);
    }

}
