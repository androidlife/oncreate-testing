package com.lftechnology.hamropay.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.events.ChatEvents;
import com.lftechnology.hamropay.events.FragmentTransactions;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ManasShrestha on 2/10/16.
 */
public class PaymentRequestView extends RelativeLayout {
    private int backgroundChatDrawable;

    @Bind(R.id.tv_title)
    TextView tvRequestedBy;

    @Bind(R.id.tv_request_amount)
    TextView tvRequestedAmount;

    @Bind(R.id.tv_date)
    TextView tvDate;

    @Bind(R.id.tv_status)
    TextView tvStatus;

    @Bind(R.id.btn_accept)
    Button btnAccept;

    @Bind(R.id.btn_decline)
    Button btnDecline;

    ChatEvents chatEvents;
    MessageModel messageModel;
    FragmentTransactions fragmentTransaction;

    LayoutParams relativeLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @Bind(R.id.rl_container)
    RelativeLayout rlContainer;

    public PaymentRequestView(Context context) {
        this(context, null, 0);
    }

    public PaymentRequestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentRequestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.payment_request_view, this, true);

        ButterKnife.bind(this);

        chatEvents = (ChatEvents) context;
        fragmentTransaction = (FragmentTransactions) context;
    }

    public void setData(MessageModel messageModel) {
        this.messageModel = messageModel;

        if (!messageModel.isReceived) {
            backgroundChatDrawable = R.drawable.chat_bubble_transparent_right_aligned;
            relativeLayoutParams.addRule(ALIGN_PARENT_END);
        } else {
            backgroundChatDrawable = R.drawable.chat_bubble_transparent_left_aligned;
            relativeLayoutParams.addRule(ALIGN_PARENT_START);
        }

        rlContainer.setLayoutParams(relativeLayoutParams);

        Bitmap mapBitmapScaled = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable);
        byte[] chunk = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable).getNinePatchChunk();
        NinePatchDrawable mapNinePatch = new NinePatchDrawable(getResources(),
                mapBitmapScaled, chunk, new Rect(), null);
        rlContainer.setBackground(mapNinePatch);

        tvRequestedBy.setText(GeneralUtils.convertToUpperCase(messageModel.receiverUserName)
                + " requested a \npayment from you.");
        tvRequestedAmount.setText("Rs." + String.valueOf(messageModel.paymentAmount));
        tvDate.setText(messageModel.date);

        tvStatus.setVisibility(INVISIBLE);
        btnAccept.setVisibility(VISIBLE);
        btnDecline.setVisibility(VISIBLE);

        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            tvStatus.setText("Payment Sent");
            hideActionButtons();
        } else if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
            tvStatus.setText("Payment Declined");
            hideActionButtons();
        }
    }

    @OnClick({R.id.btn_accept, R.id.btn_decline})
    public void setOnClicks(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                if (DbManager.getInstance().getUserBankName() != null) {
                    messageModel.paymentStatus = (PaymentRequestModel.PaymentStatus.ACCEPTED);
                    Toast.makeText(getContext(), "Payment has been accepted", Toast.LENGTH_SHORT).show();
                    chatEvents.paymentAcceptDecline(PaymentRequestModel.PaymentStatus.ACCEPTED, String.valueOf(messageModel.paymentAmount), DbManager.getInstance().getUserBankName(), null);
                    tvStatus.setText("Payment Sent");
                    hideActionButtons();
                } else {
                    fragmentTransaction.showAddBankDialog();
                }
                break;
            case R.id.btn_decline:
                messageModel.paymentStatus = (PaymentRequestModel.PaymentStatus.FAILED);
                Toast.makeText(getContext(), "Payment has been declined", Toast.LENGTH_SHORT).show();
                chatEvents.paymentAcceptDecline(PaymentRequestModel.PaymentStatus.FAILED, String.valueOf(messageModel.paymentAmount), null, null);
                tvStatus.setText("Payment Declined");
                hideActionButtons();
                break;
        }
    }

    private void hideActionButtons() {
        tvStatus.setVisibility(VISIBLE);
        btnAccept.setVisibility(GONE);
        btnDecline.setVisibility(GONE);
    }

}
