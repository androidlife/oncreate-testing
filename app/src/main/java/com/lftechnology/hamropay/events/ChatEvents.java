package com.lftechnology.hamropay.events;

import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;

/**
 * Created by ManasShrestha on 2/16/16.
 */
public interface ChatEvents {

    void sendPlainText(String textMessage);

    void paymentAcceptDecline(PaymentRequestModel.PaymentStatus paymentStatus, String paymentAmount,String bankName,String serviceName);

    void makePaymentRequest(String requestAmount,String sendDate,String additionalNotes);

    void sendSticker(MessageModel messageModel);

    void showDatePicker();

    void cancelLatestPaymentRequest(MessageModel messageModel);

    void sendPayment(MessageModel.MessageTypes sendPayment, String paymentAmount, String bankName, String serviceName);

    void acceptPayment(long messageId);

    void declinePayment(long messageId);

    void cancelPayment(long messageId);

}
