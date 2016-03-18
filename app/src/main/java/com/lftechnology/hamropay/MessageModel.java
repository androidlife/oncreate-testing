package com.lftechnology.hamropay;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lftechnology.hamropay.db.PrefManager;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;

import java.util.Calendar;

/**
 * Model for sending and receiving chat messages
 */
public class MessageModel {
    public String serviceName;
    public long messageId;

    public MessageModel() {

    }

    public enum MessageTypes {
        NORMAL_TEXT_MESSAGE,
        PHOTO_MESSAGE,
        RECEIVED_REQUEST,
        SENT_REQUEST,
        PAYMENT_RESPONSE_ACCEPT,
        PAYMENT_RESPONSE_DECLINE,
        STICKER,
        CANCEL_REQUEST,
        SEND_PAYMENT,
        ACCEPT_PAYMENT,
        DECLINE_PAYMENT,
        CANCEL_PAYMENT
    }

    public MessageTypes messageTypes;
    public boolean isReceived;
    public String date;
    public String sendDateTime;
    public PaymentRequestModel.PaymentStatus paymentStatus;
    public String baseString;

    @Nullable
    public String textMessage;

    @Nullable
    public String extraTextMessage;

    public String receiverUserName;
    public String senderUserName = PrefManager.getInstance().getRegisteredUser().getUserName();
    public int stickerType;

//    public String senderUserName;


    public int userType;

    @Nullable
    public int stickerDrawable;

    @Nullable
    public int paymentAmount;

    @Nullable
    public String bankName;

    @Nullable
    public String cardNumber;

    @Nullable
    public String photoUri;

    @Nullable
    public String additionalNotes;

    /**
     * Creates {@link MessageModel} with all fields
     *
     * @param messageTypes
     * @param isReceived
     * @param date
     * @param sendDateTime
     * @param textMessage
     * @param receiverUserName
     * @param userType
     * @param stickerDrawable
     * @param paymentAmount
     * @param bankName
     * @param cardNumber
     * @param photoUri
     * @param additionalNotes
     * @param paymentStatus
     */
    public MessageModel(MessageTypes messageTypes, Boolean isReceived, String date, String sendDateTime,
                        String textMessage, String receiverUserName, int userType, int stickerDrawable,
                        int paymentAmount, String bankName, String cardNumber, String photoUri,
                        String additionalNotes, PaymentRequestModel.PaymentStatus paymentStatus) {
        this.messageId = Calendar.getInstance().getTimeInMillis();
        this.messageTypes = messageTypes;
        this.isReceived = isReceived;
        this.date = date;
        this.sendDateTime = sendDateTime;
        this.textMessage = textMessage;
        this.receiverUserName = receiverUserName;
        this.userType = userType;
        this.stickerDrawable = stickerDrawable;
        this.paymentAmount = paymentAmount;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.photoUri = photoUri;
        this.additionalNotes = additionalNotes;
        this.paymentStatus = paymentStatus;
    }

    /**
     * Create {@link MessageModel} for message type NORMAL_TEXT_MESSAGE
     *
     * @param messageTypes
     * @param isReceived
     * @param date
     * @param receiverUserName
     * @param userType
     * @param textMessage
     */
    public MessageModel(MessageTypes messageTypes, Boolean isReceived, String date, String receiverUserName,
                        int userType, String textMessage) {
        this.messageTypes = messageTypes;
        this.messageTypes = MessageTypes.NORMAL_TEXT_MESSAGE;
        this.date = date;
        this.receiverUserName = receiverUserName;
        this.userType = userType;
        this.textMessage = textMessage;
        this.isReceived = isReceived;
        this.messageId = Calendar.getInstance().getTimeInMillis();

    }

    /**
     * Create {@link MessageModel} for message type PAYMENT_RESPONSE_ACCEPT
     *
     * @param messageTypes
     * @param isReceived
     * @param date
     * @param receiverUserName
     * @param userType
     * @param requestAmount
     * @param bankName
     * @param cardNumber
     * @param paymentStatus
     */
    public MessageModel(MessageTypes messageTypes, Boolean isReceived, String date, String receiverUserName,
                        int userType, int requestAmount, String bankName, String cardNumber, PaymentRequestModel.PaymentStatus paymentStatus) {
        this.messageTypes = messageTypes;
        this.isReceived = isReceived;
        this.date = date;
        this.userType = userType;
        this.receiverUserName = receiverUserName;
        this.paymentAmount = requestAmount;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.messageId = Calendar.getInstance().getTimeInMillis();
        this.paymentStatus = paymentStatus;

        if (paymentStatus.equals(PaymentRequestModel.PaymentStatus.PENDING)) {
            this.textMessage = "Pending Payment Request";
        } else if (paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            this.textMessage = "Payment Accepted";
        } else {
            this.textMessage = "Payment Declined";
        }
    }

    /**
     * Create {@link MessageModel} for message type PAYMENT_RESPONSE_DECLINE
     *
     * @param messageTypes
     * @param isReceived
     * @param date
     * @param receiverUserName
     * @param userType
     * @param paymentStatus
     */
    public MessageModel(MessageTypes messageTypes, Boolean isReceived, String date, String receiverUserName,
                        int userType, PaymentRequestModel.PaymentStatus paymentStatus, String extraTextMessage) {
        this.messageTypes = messageTypes;
        this.isReceived = isReceived;
        this.date = date;
        this.userType = userType;
        this.receiverUserName = receiverUserName;
        this.paymentStatus = paymentStatus;
        this.messageId = Calendar.getInstance().getTimeInMillis();

        if (paymentStatus.equals(PaymentRequestModel.PaymentStatus.PENDING)) {
            this.textMessage = "You Sent A Payment";
        } else if (paymentStatus.equals(PaymentRequestModel.PaymentStatus.ACCEPTED)) {
            this.textMessage = "Payment Accepted";
        } else {
            this.textMessage = "Payment Declined";
        }

        this.extraTextMessage = extraTextMessage;
    }

    /**
     * Creates {@link MessageModel} for message type PHOTO_MESSAGE
     *
     * @param messageTypes
     * @param isReceived
     * @param date
     * @param receiverUserName
     * @param userType
     * @param photoUri
     * @param textMessage
     */
    public MessageModel(MessageTypes messageTypes, Boolean isReceived, String date, String receiverUserName,
                        int userType, String photoUri, String textMessage, String baseString) {
        this.messageTypes = messageTypes;
        this.isReceived = isReceived;
        this.date = date;
        this.receiverUserName = receiverUserName;
        this.userType = userType;
        this.photoUri = photoUri;
        this.baseString = baseString;
        this.messageId = Calendar.getInstance().getTimeInMillis();
        if (TextUtils.isEmpty(textMessage)) {
            textMessage = "Photo message";
        }
        this.textMessage = textMessage;
    }

    /**
     * Creates {@link MessageModel} for message type STICKER
     *
     * @param messageTypes
     * @param stickerDrawable
     * @param date
     * @param receiverUserName
     * @param userType
     * @param isReceived
     */
    public MessageModel(MessageTypes messageTypes, int stickerType, int stickerDrawable, String date, String receiverUserName, int userType,
                        Boolean isReceived) {
        this.messageTypes = messageTypes;
        this.stickerDrawable = stickerDrawable;
        this.date = date;
        this.receiverUserName = receiverUserName;
        this.userType = userType;
        this.messageId = Calendar.getInstance().getTimeInMillis();
        this.isReceived = isReceived;
        this.stickerType = stickerType;
        this.textMessage = "Sticker message";
    }

    /**
     * Send Request
     *
     * @param messageTypes
     * @param date
     * @param userName
     * @param userType
     * @param sendDateTime
     * @param additionalNotes
     */
    public MessageModel(MessageTypes messageTypes,
                        int requestAmount,
                        String sendDateTime,
                        String additionalNotes,
                        String userName,
                        int userType,
                        String date) {
        this.messageTypes = messageTypes;
        this.date = date;
        this.receiverUserName = userName;
        this.paymentAmount = requestAmount;
        this.messageId = Calendar.getInstance().getTimeInMillis();
        this.userType = userType;
        this.sendDateTime = sendDateTime;
        this.textMessage = "You sent a payment request";
        this.additionalNotes = additionalNotes;
    }

    /**
     * Create {@link MessageModel} for message type RECEIVED_REQUEST
     *
     * @param messageTypes
     * @param isReceived
     * @param date
     * @param receiverUserName
     * @param userType
     * @param paymentAmount
     * @param bankName
     * @param cardNumber
     * @param additionalNotes
     * @param paymentStatus
     */
    public MessageModel(MessageTypes messageTypes, Boolean isReceived, String date,
                        String receiverUserName, int userType,
                        int paymentAmount, String bankName, String cardNumber,
                        String additionalNotes, PaymentRequestModel.PaymentStatus paymentStatus) {
        this.messageTypes = messageTypes;
        this.isReceived = isReceived;
        this.date = date;
        this.messageId = Calendar.getInstance().getTimeInMillis();
        this.receiverUserName = receiverUserName;
        this.userType = userType;
        this.paymentAmount = paymentAmount;
        this.bankName = bankName;
        this.textMessage = "You received a payment request";
        this.cardNumber = cardNumber;
        this.additionalNotes = additionalNotes;
        this.paymentStatus = paymentStatus;
    }

}
