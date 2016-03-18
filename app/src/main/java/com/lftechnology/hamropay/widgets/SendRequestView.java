package com.lftechnology.hamropay.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.events.ChatEvents;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 */
public class SendRequestView extends RelativeLayout {

    @Bind(R.id.tv_requested_amount)
    TextView tvRequestedAmount;

    @Bind(R.id.tv_date)
    TextView tvDate;

    @Bind(R.id.rl_container)
    RelativeLayout rlContainer;

    @Bind(R.id.tv_additional_notes)
    TextView tvAdditionalNotes;

    @Bind(R.id.tv_request_sent)
    TextView tvRequestSent;

    @Bind(R.id.tv_payment_deadline)
    TextView tvPaymentDeadline;

    @Bind(R.id.btn_decline_request)
    TextView btnDeclineRequest;

    @Bind(R.id.tv_request_status)
    TextView tvRequestStatus;

    Boolean isCanceled = false;

    ChatEvents chatEvents;
    MessageModel messageModel;

    public SendRequestView(Context context) {
        this(context, null);
    }

    public SendRequestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendRequestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setUpViews();
    }

    private void setUpViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.send_request_view, this, true);
        ButterKnife.bind(this);

        chatEvents = (ChatEvents) getContext();
    }

    /**
     * set data to views
     *
     * @param messageModel {@link MessageModel} corresponding to the message
     */
    public void setData(MessageModel messageModel) {
        this.messageModel = messageModel;
        LayoutParams relativeLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int backgroundChatDrawable;

        backgroundChatDrawable = R.drawable.chat_bubble_transparent_right_aligned;
        relativeLayoutParams.addRule(ALIGN_PARENT_END);

        rlContainer.setLayoutParams(relativeLayoutParams);

        Bitmap mapBitmapScaled = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable);
        // Load the 9-patch data chunk and apply to the view
        byte[] chunk = BitmapFactory.decodeResource(getResources(), backgroundChatDrawable).getNinePatchChunk();
        NinePatchDrawable mapNinePatch = new NinePatchDrawable(getResources(),
                mapBitmapScaled, chunk, new Rect(), null);
        rlContainer.setBackground(mapNinePatch);

        tvRequestedAmount.setText("Rs. " + messageModel.paymentAmount);
        tvPaymentDeadline.setText("Send before " + messageModel.sendDateTime);
        if (!TextUtils.isEmpty(messageModel.additionalNotes)) {
            tvAdditionalNotes.setVisibility(VISIBLE);
            tvAdditionalNotes.setText(messageModel.additionalNotes);
        }

        tvDate.setText(messageModel.date);

        if (messageModel.paymentStatus == null) {
            messageModel.paymentStatus = PaymentRequestModel.PaymentStatus.PENDING;
        }

        if (messageModel.paymentStatus.equals(PaymentRequestModel.PaymentStatus.FAILED)) {
            tvRequestStatus.setVisibility(VISIBLE);
            btnDeclineRequest.setVisibility(INVISIBLE);
        } else {
            btnDeclineRequest.setVisibility(VISIBLE);
            tvRequestStatus.setVisibility(INVISIBLE);
        }
    }

    @OnClick(R.id.btn_decline_request)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_decline_request:
                chatEvents.cancelLatestPaymentRequest(messageModel);
                Timber.e("decline");
                tvRequestStatus.setVisibility(VISIBLE);
                btnDeclineRequest.setVisibility(GONE);
                messageModel.paymentStatus = PaymentRequestModel.PaymentStatus.FAILED;
                messageModel.textMessage = "Request Canceled";
                break;
        }
    }

}
