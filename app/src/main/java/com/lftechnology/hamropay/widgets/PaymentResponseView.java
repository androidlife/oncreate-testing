package com.lftechnology.hamropay.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ManasShrestha on 2/10/16.
 */
public class PaymentResponseView extends RelativeLayout {

    @Bind(R.id.tv_info)
    TextView tvRequestedBy;

    @Bind(R.id.tv_requested_amount)
    TextView tvRequestedAmount;

    @Bind(R.id.tv_bank_info)
    TextView tvBankInfo;

    @Bind(R.id.tv_date)
    TextView tvDate;

    @Bind(R.id.tv_service_type)
    TextView tvServiceType;

    @Bind(R.id.iv_check)
    ImageView ivCheck;

    @Bind(R.id.rl_container)
    RelativeLayout rlContainer;


    public PaymentResponseView(Context context) {
        this(context, null, 0);
    }

    public PaymentResponseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentResponseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.payment_accept_decline_status_view, this, true);

        ButterKnife.bind(this);
    }

    /**
     * set data to views
     *
     * @param messageModel {@link MessageModel} corresponding to the message
     */
    public void setData(MessageModel messageModel) {
        RelativeLayout.LayoutParams relativeLayoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        int backgroundChatDrawable;

        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {

            if (!messageModel.isReceived) {
                backgroundChatDrawable = R.drawable.chat_bubble_right_aligned_green;
                relativeLayoutParams.addRule(ALIGN_PARENT_END);
            } else {
                backgroundChatDrawable = R.drawable.chat_bubble_left_aligned_green;
                relativeLayoutParams.addRule(ALIGN_PARENT_START);
            }
        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.CANCELED)) {
            if (!messageModel.isReceived) {
                backgroundChatDrawable = R.drawable.chat_bubble_transparent_right_aligned;
                relativeLayoutParams.addRule(ALIGN_PARENT_END);
            } else {
                backgroundChatDrawable = R.drawable.chat_bubble_transparent_left_aligned;
                relativeLayoutParams.addRule(ALIGN_PARENT_START);
            }
        } else {
            if (!messageModel.isReceived) {
                backgroundChatDrawable = R.drawable.chat_bubble_right_aligned_green;
                relativeLayoutParams.addRule(ALIGN_PARENT_END);
            } else {
                backgroundChatDrawable = R.drawable.chat_bubble_left_aligned_green;
                relativeLayoutParams.addRule(ALIGN_PARENT_START);
            }
        }

        rlContainer.setLayoutParams(relativeLayoutParams);

        Bitmap mapBitmapScaled = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable);
        // Load the 9-patch data chunk and apply to the view
        byte[] chunk = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable).getNinePatchChunk();
        NinePatchDrawable mapNinePatch = new NinePatchDrawable(getResources(),
                mapBitmapScaled, chunk, new Rect(), null);

        tvBankInfo.setTextColor(Color.parseColor("#9b9b9b"));
        tvRequestedAmount.setTextColor(Color.parseColor("#202340"));
        tvRequestedBy.setTextColor(Color.parseColor("#5b5c59"));
        tvServiceType.setTextColor(Color.parseColor("#5b5c59"));
        ivCheck.setVisibility(VISIBLE);
        // dynamically change color
//        mapNinePatch.setColorFilter(0, PorterDuff.Mode.CLEAR);
        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            mapNinePatch.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark),
                    PorterDuff.Mode.MULTIPLY));
            tvBankInfo.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedAmount.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedBy.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvServiceType.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            ivCheck.setVisibility(GONE);
        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
            mapNinePatch.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark),
                    PorterDuff.Mode.MULTIPLY));
            tvBankInfo.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedAmount.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedBy.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvServiceType.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            ivCheck.setVisibility(GONE);
        }

        rlContainer.setBackground(mapNinePatch);

        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            if (messageModel.isReceived) {
                tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.senderUserName)
                        + " sent you a payment.");
            } else {
                tvRequestedBy.setText("You sent " + GeneralUtils.convertToUpperCase(messageModel.receiverUserName)
                        + " a payment.");
            }

            if (messageModel.userType == User.TYPE_SERVICE) {
                tvServiceType.setVisibility(VISIBLE);
                tvServiceType.setText(messageModel.serviceName);
            }

            tvBankInfo.setText("via " + messageModel.bankName + "\n #### #### #### " + messageModel.cardNumber);
            tvRequestedAmount.setText("Rs. " + messageModel.paymentAmount);
            tvBankInfo.setVisibility(VISIBLE);
            tvRequestedAmount.setVisibility(VISIBLE);
            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_checked));

        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
            if (messageModel.extraTextMessage != null) {
                tvRequestedBy.setText(messageModel.extraTextMessage);
            } else {
                if (messageModel.isReceived) {
                    tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.senderUserName)
                            + " declined the payment");
                } else {
                    tvRequestedBy.setText("You declined the payment");
                }
            }

            tvBankInfo.setVisibility(GONE);
            tvRequestedAmount.setVisibility(GONE);

            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel_filled));
        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.CANCELED)) {
            if (messageModel.extraTextMessage != null) {
                tvRequestedBy.setText(messageModel.extraTextMessage);
            } else {
                if (messageModel.isReceived) {
                    tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.senderUserName)
                            + " canceled the payment");
                } else {
                    tvRequestedBy.setText("You declined the payment");
                }
            }

            tvBankInfo.setVisibility(GONE);
            tvRequestedAmount.setVisibility(GONE);

            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel_filled));
        }

        tvDate.setText(messageModel.date);
    }


}
