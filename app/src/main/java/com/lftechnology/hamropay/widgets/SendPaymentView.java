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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.events.ChatEvents;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ManasShrestha on 2/10/16.
 */
public class SendPaymentView extends RelativeLayout {

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

    @Bind(R.id.btn_decline_payment)
    Button btnDeclinePayment;

    @Bind(R.id.btn_accept_payment)
    Button btnAcceptPayment;
    MessageModel messageModel;
    ChatEvents chatEvents;

    public SendPaymentView(Context context) {
        this(context, null, 0);
    }

    public SendPaymentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendPaymentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.send_payment_view, this, true);

        ButterKnife.bind(this);

        chatEvents = (ChatEvents) context;
    }

    /**
     * set data to views
     *
     * @param messageModel {@link MessageModel} corresponding to the message
     */
    public void setData(MessageModel messageModel) {
        this.messageModel = messageModel;
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        int backgroundChatDrawable;

        if (!messageModel.isReceived) {
            backgroundChatDrawable = R.drawable.chat_bubble_transparent_right_aligned;
            if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)
                    || messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
                backgroundChatDrawable = R.drawable.chat_bubble_right_aligned_green;
            }
            relativeLayoutParams.addRule(ALIGN_PARENT_END);
            btnDeclinePayment.setText("CANCEL");
        } else {
            backgroundChatDrawable = R.drawable.chat_bubble_transparent_left_aligned;
            if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)
                    || messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
                backgroundChatDrawable = R.drawable.chat_bubble_left_aligned_green;
            }
            relativeLayoutParams.addRule(ALIGN_PARENT_START);
            btnDeclinePayment.setText("DECLINE");
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
        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            mapNinePatch.setColorFilter(0, PorterDuff.Mode.CLEAR);
            mapNinePatch.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark),
                    PorterDuff.Mode.MULTIPLY));
            tvBankInfo.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedAmount.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedBy.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvServiceType.setTextColor(Color.parseColor("#ffffff"));
            ivCheck.setVisibility(GONE);
        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
            mapNinePatch.setColorFilter(0, PorterDuff.Mode.CLEAR);
            mapNinePatch.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark),
                    PorterDuff.Mode.MULTIPLY));
            tvBankInfo.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvRequestedAmount.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tvServiceType.setTextColor(Color.parseColor("#ffffff"));
            tvRequestedBy.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            ivCheck.setVisibility(GONE);
        }

        rlContainer.setBackground(mapNinePatch);

        if (messageModel.isReceived) {
            btnAcceptPayment.setVisibility(VISIBLE);
            btnDeclinePayment.setVisibility(VISIBLE);
        } else {
            btnDeclinePayment.setVisibility(VISIBLE);
            btnAcceptPayment.setVisibility(GONE);
        }

        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            Log.e("+++", "Accepted");

            if (messageModel.isReceived) {
                tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.senderUserName) + " sent you a payment.");

            } else {
                tvRequestedBy.setText("You sent " + GeneralUtils.convertToUpperCase(messageModel.receiverUserName) + " a payment.");
            }

            if (messageModel.userType == User.TYPE_SERVICE) {
                tvServiceType.setVisibility(VISIBLE);
                tvServiceType.setText(messageModel.serviceName);
            }

            btnDeclinePayment.setVisibility(GONE);
            btnAcceptPayment.setVisibility(GONE);

            tvBankInfo.setText("via " + messageModel.bankName + "\n #### #### #### " + messageModel.cardNumber);
            tvRequestedAmount.setText("Rs. " + messageModel.paymentAmount);
            tvBankInfo.setVisibility(VISIBLE);
            tvRequestedAmount.setVisibility(VISIBLE);
            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_checked));

        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.CANCELED)) {
            if (messageModel.isReceived) {
                tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.senderUserName) + " canceled the payment");
            } else {
                tvRequestedBy.setText("You canceled the payment");
            }

            tvBankInfo.setVisibility(GONE);
            tvRequestedAmount.setVisibility(GONE);

            btnDeclinePayment.setVisibility(GONE);
            btnAcceptPayment.setVisibility(GONE);

            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel_filled));

        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
            if (messageModel.isReceived) {
                tvRequestedBy.setText("You declined the payment");
            } else {
                tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.receiverUserName) + " declined the payment");
            }

            tvBankInfo.setVisibility(GONE);
            tvRequestedAmount.setVisibility(GONE);

            btnDeclinePayment.setVisibility(GONE);
            btnAcceptPayment.setVisibility(GONE);

            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel_filled));

        } else {

            if (messageModel.isReceived) {
                tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.senderUserName) + " sent you a payment.");
            } else {
                tvRequestedBy.setText("You sent " + GeneralUtils.convertToUpperCase(messageModel.receiverUserName) + " a payment.");
            }

            tvRequestedBy.setVisibility(VISIBLE);
            tvBankInfo.setText("via " + messageModel.bankName + "\n #### #### #### " + messageModel.cardNumber);
            tvRequestedAmount.setText("Rs. " + messageModel.paymentAmount);
            tvBankInfo.setVisibility(VISIBLE);
            tvRequestedAmount.setVisibility(VISIBLE);
            ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_checked));

        }

        tvDate.setText(messageModel.date);
    }

    @OnClick({R.id.btn_decline_payment, R.id.btn_accept_payment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_decline_payment:
                btnDeclinePayment.setVisibility(GONE);
                btnAcceptPayment.setVisibility(GONE);
                messageModel.paymentStatus = PaymentRequestModel.PaymentStatus.FAILED;

                tvBankInfo.setVisibility(GONE);
                tvRequestedAmount.setVisibility(GONE);
                ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancel_filled));
                if (messageModel.isReceived) {
                    chatEvents.declinePayment(messageModel.messageId);
                    tvRequestedBy.setText("You declined payment from " + GeneralUtils.convertToUpperCase(messageModel.senderUserName));
                } else {
                    tvRequestedBy.setText("You canceled the payment");
                    messageModel.paymentStatus = PaymentRequestModel.PaymentStatus.CANCELED;
                    chatEvents.cancelPayment(messageModel.messageId);
                }
                break;
            case R.id.btn_accept_payment:
                btnDeclinePayment.setVisibility(GONE);
                btnAcceptPayment.setVisibility(GONE);
                messageModel.paymentStatus = PaymentRequestModel.PaymentStatus.ACCEPTED;
                tvRequestedBy.setText("You accepted payment from \n" + GeneralUtils.convertToUpperCase(messageModel.receiverUserName));
                tvBankInfo.setVisibility(VISIBLE);
                tvRequestedAmount.setVisibility(VISIBLE);
                ivCheck.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_checked));
                chatEvents.acceptPayment(messageModel.messageId);
                break;
        }
    }

}
